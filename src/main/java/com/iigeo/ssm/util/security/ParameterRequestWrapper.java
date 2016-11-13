package com.iigeo.ssm.util.security;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class ParameterRequestWrapper extends HttpServletRequestWrapper{
	
	private Map<String , String[]> params = new HashMap<String, String[]>();  
	  
    public ParameterRequestWrapper(HttpServletRequest request) {  
        super(request);  
        this.params.putAll(request.getParameterMap());  
    }  
    public ParameterRequestWrapper(HttpServletRequest request , Map<String , Object> extendParams) {  
        this(request);  
        addAllParameters(extendParams);
    }  
    
    @Override
    public Map<String, String[]> getParameterMap() {
    	return params;
    }
    
    @Override  
    public String getParameter(String name) {
        String[]values = params.get(name);  
        if(values == null || values.length == 0) {  
            return null;  
        }  
        return values[0];  
    }  
   
    @Override
    public Enumeration<String> getParameterNames() {
    	Vector<String> vector=new Vector<String>(params.keySet());
    	return vector.elements();
    }
    
    
    @Override
    public String[] getParameterValues(String name) {
         return params.get(name);  
    }  
  
   public void addAllParameters(Map<String , Object>otherParams) {
        for(Map.Entry<String , Object>entry : otherParams.entrySet()) {  
            addParameter(entry.getKey() , entry.getValue());  
        }  
    }  
  
    public void addParameter(String name , Object value) {
        if(value != null) {  
            if(value instanceof String[]) {  
                params.put(name , (String[])value);  
            }else if(value instanceof String) {  
                params.put(name , new String[] {(String)value});  
            }else {  
                params.put(name , new String[] {String.valueOf(value)});  
            }  
        }  
    }  
}
