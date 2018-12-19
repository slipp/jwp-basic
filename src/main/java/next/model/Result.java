package next.model;

public class Result {
	private boolean status;
	private String message;
	
	public Result(boolean status) {
		this(status,"");
	}
	
	public Result(boolean status,String message) {
		this.status=status;
		this.message=message;
	}
	
	public static Result Ok() {
		return new Result(true);
	}
	
	public static Result Fail(String message) {
		return new Result(false,message);
	}
	
	public boolean isStatus() {
		return this.status;
	}
	public String getmessage() {
		return this.message;
	}
	public String toString() {
		return "Result [Status = "+status+", message="+message+"]";
	}
}
