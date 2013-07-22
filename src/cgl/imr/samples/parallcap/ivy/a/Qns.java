package cgl.imr.samples.parallcap.ivy.a;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.*;

public class Qns {
	private static List<Node> queryNodes;
	private static int numOfCoverage;
	private static int sizeOfQueryNodes;
	private static int length;
	private static List<List<Node>> candidateQns;
	
	public Qns() {
		FileInputStream fstream = null;
		
		try {
			fstream = new FileInputStream("./constraints");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Get the object of DataInputStream
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String strline;

		//Read File Line By Line
		try {
			while ((strline = br.readLine()) != null)   {
			  // Print the content on the console
			  System.out.println (strline);
			  String[] splits = strline.split("=");
			  if (splits[0].equalsIgnoreCase(CAPConstraints.coverageNum)) {
				  numOfCoverage = Integer.parseInt(splits[1]);
			  } else if (splits[0].equalsIgnoreCase(CAPConstraints.length)) {
				  length = Integer.parseInt(splits[1]);
			  } else if (splits[0].equalsIgnoreCase(CAPConstraints.queryNodes)) {
				  queryNodes = new ArrayList<Node>();
				  String[] queryIds = splits[1].split(",");
				  for (String elem : queryIds) {
					  Node node = new Node(Integer.parseInt(elem));
					  queryNodes.add(node);
				  }
				  sizeOfQueryNodes = queryNodes.size();
			  } else {
				  System.out.println("Invalid parameter, exit!");
				  System.exit(1);
			  }
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//Close the input stream
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public List<Node> getQueryNodes() {
		return queryNodes;
	}

	
	
	public static int getNumOfCoverage() {
		return numOfCoverage;
	}

	public static void setNumOfCoverage(int numOfCoverage) {
		Qns.numOfCoverage = numOfCoverage;
	}

	public static int getSizeOfQueryNodes() {
		return sizeOfQueryNodes;
	}

	public static void setSizeOfQueryNodes(int sizeOfQueryNodes) {
		Qns.sizeOfQueryNodes = sizeOfQueryNodes;
	}

	public static int getLength() {
		return length;
	}

	public static void setLength(int length) {
		Qns.length = length;
	}

	public static List<List<Node>> getCandidateQns() {
		return candidateQns;
	}

	public static void setCandidateQns(List<List<Node>> candidateQns) {
		Qns.candidateQns = candidateQns;
	}

	public static void setQueryNodes(List<Node> queryNodes) {
		Qns.queryNodes = queryNodes;
	}

	private List<List<Node>> permutationHelper(List<Node> permNodes) {
		List<List<Node>> permRes = new ArrayList<List<Node>>();
		List<int[]> permIndexList = permutationHelperImpl(permNodes.size());
		
		for (int[] elem : permIndexList) {
			List<Node> tmp = new ArrayList<Node>();
			for (int i : elem) {
				tmp.add(permNodes.get(i));
			}
			permRes.add(tmp);
		}
		
		return permRes;
	}
	
	public List<List<Node>> generateCandQns() {
		candidateQns = new ArrayList<List<Node>>();
		if (length > sizeOfQueryNodes + 1) return null;
		
		//generate k-permutation sequences from query nodes list
		List<List<Node>> tmpCombRes = null;
		for (int k = 1; k <= numOfCoverage; k++) {
			tmpCombRes = combinationHelper(k, sizeOfQueryNodes);
			for (List<Node> comb : tmpCombRes) {
				candidateQns.addAll(permutationHelper(comb));
			}
		}
		
		return candidateQns;
	}
	
	//implementation of combination generator of "the art of computer programming"
	//section 7.2.2.2
	private List<List<Node>> combinationHelper(int k, int n) {
		List<List<Node>> res = new ArrayList<List<Node>>();
		List<int[]> combRes = combinationHelperImpl(k, n);
		for (int[] elem : combRes) {
			Arrays.sort(elem);
			List<Node> tmp = new ArrayList<Node>();
			for (int i : elem) {
				tmp.add(queryNodes.get(i));
			}
			res.add(new ArrayList<Node>(tmp));
		}
		return res;
	}
	
	private List<int[]> combinationHelperImpl(int k, int n) {
		//Initial
		List<int[]> res = new ArrayList<int[]>();
		int[] elems = new int[n];
		int[] aux = new int[(k+2)];
		int i = 0;
		while (i < n) {
			elems[i] = i;
			i++;
		}
		i = 0;
		while (i < k) {
			aux[i] = i;
			i++;
		}
		//two more auxiliary elems used as sentinel
		aux[k] = n;
		aux[k + 1] = 0;
		//Initial complete
		
		int j = 0;
		int[] visit = null;
		while (true) {
			//add current combination
			j = 0;
			visit = new int[k];
			for (i = 0; i < k; i++) {
				visit[i] = aux[i];
			}
			res.add(visit);
			
			while (aux[j] + 1 == aux[j+1]) {
				aux[j] = j;
				j += 1;
			}
			if (j >= k) break;
			else {
				aux[j] += 1;
			}
		}
		return res;
	}
	
	private List<int[]> permutationHelperImpl(int k) {
		List<int[]> res = new ArrayList<int[]>();
		int[] elemToPerm = new int[k];
		for(int i = 0; i < k; i++) {
			elemToPerm[i] = i;
		}
		
		int size = factorial(k);
		int num = 0;
		while (num++ < size) {
			//visit current elems
			int visit[] = new int[k];
			visit = Arrays.copyOf(elemToPerm, elemToPerm.length);
			res.add(visit);
			//find j
			int j = k-1;
			while (j > 0 && elemToPerm[j-1] > elemToPerm[j]) j--;
			if (j == 0) return res;
			
			//find i
			int i = k-1;
			while (i >= 0 && elemToPerm[i] < elemToPerm[j-1]) i--;
			
			//swap elem[i] and elem[j]
			swap(i, j-1, elemToPerm);
			
			//reverse elem[] from j to end
			int m = j;
			int n = k-1;
			while (n - m > 0) {
				swap(m, n, elemToPerm);
				m++;
				n--;
			}
		}
		return res;
	}
	
	private void swap(int i, int j, int[] elem) {
		int tmp = elem[i];
		elem[i] = elem[j];
		elem[j] = tmp;
	}
	
	private int factorial(int k) {
		if (k == 0) return 1;
		return k * factorial(k-1);
	}
	
}
