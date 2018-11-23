package demo.model;

public class InfoProblem {
	private String name;
	private String cause;

	public String getName() {
		String nameMethod = "getName";
		return name;
	}

	public void setName(String name) {
		String nameMethod = "setName";
		this.name = name;
	}

	public String getCause() {
		String nameMethod = "getCause";
		return cause;
	}

	public void setCause(String cause) {
		String nameMethod = "setCause";
		this.cause = cause;
	}

	public InfoProblem(String name, String cause) {
		String nameMethod = "InfoProblem";
		this.name = name;
		this.cause = cause;
	}

	public InfoProblem() {

		// TODO Auto-generated constructor stub
	}

}
