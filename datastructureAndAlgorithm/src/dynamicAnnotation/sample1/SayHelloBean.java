package dynamicAnnotation.sample1;

import dynamicAnnotation.annotation.PersonneName;

public class SayHelloBean {

	private static final String HELLO_MSG = "Hello ";

	@PersonneName(name = "")
	public String sayHelloTo(String name) {
		return HELLO_MSG + name;
	}
}