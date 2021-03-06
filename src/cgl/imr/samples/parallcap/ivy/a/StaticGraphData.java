package cgl.imr.samples.parallcap.ivy.a;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import cgl.imr.base.SerializationException;
import cgl.imr.base.TwisterMessage;
import cgl.imr.base.Value;

public class StaticGraphData {
	private int numOfNodes;
	private List<List<Node>> adjlist;
	
	public List<List<Node>> getAdjlist() {
		return adjlist;
	}

	public void setAdjlist(List<List<Node>> adjlist) {
		this.adjlist = adjlist;
	}

	public int getNumOfNodes() {
		return numOfNodes;
	}

	public void setNumOfNodes(int numOfNodes) {
		this.numOfNodes = numOfNodes;
	}
	
	public StaticGraphData() {
		numOfNodes = 0;
		adjlist = new ArrayList<List<Node>>();
	}
	
	/*
	 * loads data from a text file.
	 * Format: src_node_id adj1 adj2 ... adjn
	 * */
	public void loadDataFromTextFile(String fileName) throws Exception {
		BufferedReader reader = null;
		
		reader = new BufferedReader(new FileReader(new File(fileName)));
		this.numOfNodes = Integer.parseInt(reader.readLine());
		
		for (int i = 0; i < this.numOfNodes; i++) {
			String inputLine = reader.readLine();
			String[] nodeIds = inputLine.split(" ");
			List<Node> oneAdjlist = new ArrayList<Node>();
			for (String ids : nodeIds) {
				int id = Integer.parseInt(ids);
				Node node = new Node(id);
				oneAdjlist.add(node);
			}
			adjlist.add(oneAdjlist);
		}
		
		reader.close();
	
	}
}










































