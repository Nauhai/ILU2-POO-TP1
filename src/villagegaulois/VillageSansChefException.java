package villagegaulois;

public class VillageSansChefException extends Exception {
	public VillageSansChefException() {
		super();
	}
	
	public VillageSansChefException(String msg) {
		super(msg);
	}
	
	public VillageSansChefException(Throwable cause) {
		super(cause);
	}
	
	public VillageSansChefException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
