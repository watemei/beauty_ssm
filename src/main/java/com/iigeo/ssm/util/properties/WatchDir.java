package com.iigeo.ssm.util.properties;

import static java.nio.file.LinkOption.NOFOLLOW_LINKS;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.iigeo.ssm.util.ApplicationContextHolder;

public class WatchDir implements Runnable {
	private static Logger LOG = LogManager.getLogger(WatchDir.class);

	private boolean recursive = false;
	private final WatchService watcher;
	private final Map<WatchKey, Path> keys;
	private boolean trace;

	public WatchDir(Path path, boolean recursive) throws IOException {
		this.watcher = FileSystems.getDefault().newWatchService();
		this.keys = new HashMap<WatchKey, Path>();
		this.recursive = recursive;

		// 遍历
		if (recursive) {
			System.out.format("Scanning %s ...\n", path);
			registerAll(path);
			System.out.println("Done.");
		} else {
			register(path);
		}
		// enable trace after initial registration
		this.trace = true;
	}

	/**
	 * Register the given directory with the WatchService
	 */
	private void register(Path dir) throws IOException {
		WatchKey key = dir.register(watcher, ENTRY_CREATE, ENTRY_DELETE,
				ENTRY_MODIFY);
		if (trace) {
			Path prev = keys.get(key);
			if (prev == null) {
				System.out.format("register: %s\n", dir);
			} else {
				if (!dir.equals(prev)) {
					System.out.format("update: %s -> %s\n", prev, dir);
				}
			}
		}
		keys.put(key, dir);
	}

	/**
	 * Register the given directory, and all its sub-directories, with the
	 * WatchService.
	 */
	private void registerAll(final Path start) throws IOException {
		Stream.of(start.toFile().listFiles())
				.flatMap(
						file -> file.listFiles() == null ? Stream.of(file)
								: Stream.of(file.listFiles()))
				.filter(file -> file.getName().endsWith(".properties"))
				.forEach(file -> {
					try {
						register(file.toPath());
					} catch (Exception e) {
						LOG.error(e);
					}
				});

	}

	@SuppressWarnings("unchecked")
	static <T> WatchEvent<T> cast(WatchEvent<?> event) {
		return (WatchEvent<T>) event;
	}

	@Override
	public void run() {
		for (;;) {
			// wait for key to be signalled
			WatchKey key;
			try {
				key = watcher.take();
			} catch (InterruptedException x) {
				return;
			}

			Path dir = keys.get(key);
			if (dir == null) {
				System.out.println("WatchKey not recognized!!");
				continue;
			}

			for (WatchEvent<?> event : key.pollEvents()) {
				@SuppressWarnings("rawtypes")
				WatchEvent.Kind kind = event.kind();

				// TBD - provide example of how OVERFLOW event is handled
				if (kind == OVERFLOW) {
					continue;
				}

				// Context for directory entry event is the file name of entry
				WatchEvent<Path> ev = cast(event);
				Path name = ev.context();
				Path child = dir.resolve(name);
				if (kind == ENTRY_MODIFY) {
					try {
						// 事件启动
						PropertiesEventObject p = new PropertiesEventObject();
						p.setChangePath(ev.context());
						p.setRecursive(recursive);
						ApplicationContextHolder.getApplicationContext().publishEvent(p);
					} catch (Exception e) {
						LOG.error(e);
					}
					// print out event
					System.out.format("%s: %s\n", event.kind().name(), child);
				}
				
				

				// if directory is created, and watching recursively, then
				// register it and its sub-directories
				if (recursive && (kind == ENTRY_CREATE)) {
					try {
						if (Files.isDirectory(child, NOFOLLOW_LINKS)) {
							registerAll(child);
						}
					} catch (IOException x) {
						// ignore to keep sample readbale
						LOG.error(x);
					}
				}
			}

			// reset key and remove from set if directory no longer accessible
			boolean valid = key.reset();
			if (!valid) {
				keys.remove(key);

				// all directories are inaccessible
				if (keys.isEmpty()) {
					break;
				}
			}
		}
		
	}

}