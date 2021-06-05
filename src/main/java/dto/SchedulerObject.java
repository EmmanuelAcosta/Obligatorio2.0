package dto;

public class SchedulerObject {
	private int timeInMilis;
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getTimeInMilis() {
		return timeInMilis;
	}
	public void setTimeInMilis(int timeInMilis) {
		this.timeInMilis = timeInMilis;
	}
}
