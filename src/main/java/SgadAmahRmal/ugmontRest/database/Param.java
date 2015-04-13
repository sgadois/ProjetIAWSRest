package SgadAmahRmal.ugmontRest.database;

public class Param {

	public enum TypeSearch {
		EQUAL,
		CONTAINS
	}
	
	private String name;
	private String value;
	private TypeSearch type;
	
	public Param(String name, String value, TypeSearch type) {
		this.name = name;
		this.value = value;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public String getValue() {
		return value;
	}

	public TypeSearch getType() {
		return type;
	}
	
}
