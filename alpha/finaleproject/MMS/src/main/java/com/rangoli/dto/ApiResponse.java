package com.rangoli.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    public boolean status;
    public String message;
    public Object data;
	private String token;
	private Integer remainingAttempts;

    public ApiResponse(boolean status, String message,String token) {
        this.status = status;
        this.message = message;
        this.token = token;
        this.remainingAttempts =remainingAttempts;
    }

   public ApiResponse(boolean status, String message, Object data) {
      this.status = status;
       this.message = message;
        this.data = data;
   }

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getRemainingAttempts() {
		return remainingAttempts;
	}

	public void setRemainingAttempts(Integer remainingAttempts) {
		this.remainingAttempts = remainingAttempts;
	}
    
}