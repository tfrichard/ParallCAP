package cgl.imr.samples.parallcap.ivy.a;

import java.util.List;

public class Node {
	private int id;
	private String tag;
	List<Node> adjList;
	
	public long getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public List<Node> getAdjList() {
		return adjList;
	}
	public void setAdjList(List<Node> adjList) {
		this.adjList = adjList;
	}
	
	
}
