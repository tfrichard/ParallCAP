package cgl.imr.samples.parallcap.ivy.a;

import java.util.*;

public class Qns {
	private List<Node> queryNodes;
	private int numOfCoverage;
	private int sizeOfQueryNodes;
	private int length;
	private Node src;
	private Node dest;
	private List<List<Node>> candidateQns;

	private void permutationHelper() {}
	//private void nextPermutation(List<Node>) {}
	
	public List<List<Node>> generateCandQns() {
		candidateQns = null;
		if (length >= sizeOfQueryNodes) return candidateQns;
		
		//generate k-permutation sequences from query nodes list
		List<List<Node>> localPermRes = null;
		for (int k = 1; k <= numOfCoverage; k++) {
			localPermRes = combinationHelper(k, numOfCoverage);
			//permutationHelper(k, localPermRes);
		}
		
		return candidateQns;
	}
	
	//implementation of combination generator of "the art of computer programming"
	//section 7.2.2.2
	private List<List<Node>> combinationHelper(int k, int n) {
		//Initial
		List<List<Node>> res = new ArrayList<List<Node>>();
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
	
	public List<int[]> permutationHelperImpl(int k) {
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
