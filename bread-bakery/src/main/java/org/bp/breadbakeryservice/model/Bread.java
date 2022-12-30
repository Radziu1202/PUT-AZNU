package org.bp.breadbakeryservice.model;

public class Bread {

    private Boolean glutenFree;
    private String name;
    private String breadType;
    
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getBreadType() {
		return breadType;
	}
	public void setBreadType(String breadType) {
		this.breadType = breadType;
	}
	public Boolean getGlutenFree() {
		return glutenFree;
	}
	public void setGlutenFree(Boolean glutenFree) {
		this.glutenFree = glutenFree;
	}
}