package com.smithdrug.sms.model;
import java.util.Map;
public class fpsEmailModel {
	
	    private String from;
	    private String to;
	    private String subject;
	    private Map<String, Object> fpsModel;

	    public fpsEmailModel() {

	    }

	    public String getFrom() {
	        return from;
	    }

	    public void setFrom(String from) {
	        this.from = from;
	    }

	    public String getTo() {
	        return to;
	    }

	    public void setTo(String to) {
	        this.to = to;
	    }

	    public String getSubject() {
	        return subject;
	    }

	    public void setSubject(String subject) {
	        this.subject = subject;
	    }

	    public Map<String, Object> getFpsModel() {
	        return fpsModel;
	    }

	    public void setFpsModel(Map<String, Object> fpsModel) {
	        this.fpsModel = fpsModel;
	    }
}
