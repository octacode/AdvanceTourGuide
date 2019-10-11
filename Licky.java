class Demo {
  private String firstName;

public void setFirstName(String fname) {
	this.firstName = fname;
}

public String getFirstName() {
	return this.firstName;
}
  
  public static void main(String... args) {
    Demo d = new Demo();
    d.setFirstName("Licky Class");
    System.out.println(d.getFirstName);
  }
}
