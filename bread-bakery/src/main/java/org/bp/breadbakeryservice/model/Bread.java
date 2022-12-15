package org.bp.breadbakeryservice.model;

public class Bread {

    private boolean isGlutenFree;
    private String name;
    private String breadType;
    
	public boolean isIsGlutenFree() {
		return isGlutenFree;
	}
	public void setIsGlutenFree(boolean isGlutenFree) {
		this.isGlutenFree = isGlutenFree;
	}
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
}