package monitor;


public interface Monitor {
	
	
	public abstract String getTime();
	public abstract void update(String [] rainfall, String [] temperature);

}
