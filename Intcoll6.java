
//Ogadinma Enwereji
//Date: 2/14/19
//Assignment No.4

import java.util.*;
import java.io.*;

public class Intcoll6 {
	
	   private int howmany; //declaring counting variable
	   private btNode c; //declare variable of type btNode
		
	 //default constructor
	   public Intcoll6()
	   {
	      	c=null;
		    howmany=0;
	   }

	 //alternate constructor
	   public Intcoll6(int i)
	   {
		   c=null;
		    howmany=0;
	   }
	   
	   //This method returns a copy of a given btNode variable starting from the root node
	   private static btNode copytree(btNode t) {
		   btNode root=null;
		   
		   if(t!=null) {
			   root=new btNode(); //declare new btNode variable
			   root.info=t.info;
			   root.left=copytree(t.left);
			   root.right=copytree(t.right);
		   }
		   return root; 
	   }

	   //this method calls the copytree method above
	   public void copy(Intcoll6 obj)
	   {
		    if(this!=obj) {
		    	howmany=obj.howmany;
		    	c=copytree(obj.c);
		    }
		}

	   //this method determines whether an integer belongs in the binary tree
	   public boolean belongs(int i)
	   {
		   btNode p=c; //have btNode variable 'p' point to 'c'
		   while((p!=null)&&(p.info!=i)) { //if 'p' is not null and the integer is not equal to the info member of 'p'
			   if(i<p.info) p=p.left; //if integer is less than info member, go to left side of 'p'
			   else p=p.right; //else, go to the right subtree
		   }
		   return (p!=null);
	   }

	   /*In the insert method, two btNode variables are declare: one preceding the other. Once 'p' reaches null
	    * increase counting variable, declare a new btNode for 'p' with inserted integer and place into binary tree*/
	   public void insert(int i)
	   {
		   if(i>0) { //check whether integer is positive
			   btNode pred=null, p=c;
			   while((p!=null)&&(p.info!=i)) {
				   pred=p;
				   if(i<p.info) p=p.left;
				   else p=p.right;
			   }
			   if(p==null) { 
				   howmany++; p=new btNode(i, null, null);
				   if(pred!=null) {
					   if(i<pred.info) pred.left=p;
					   else pred.right=p;
			   }else c=p;
			   }
		   }
	   }

	   /*In the omit method, a node is deleted based on the following precautions, 
	    * specifically whether or not the node has children.*/
	   public void omit(int i)
	   {
		   if(i>0) {
			   btNode pred=null, p=c;
			   while((p!=null)&&(p.info!=i)) {
				   pred=p;
				   if(i<pred.info) p=p.left;
				   else p=p.right;
			   }
			   
			   //boolean variable that determines whether 'p' is the left successor of 'pred' variable
			   boolean isLeft; 
			   
			   if((p.left==null)&&p.right==null) { //if node has no successors
				   if(pred==null) { c=null;
				   }else {
					   isLeft = (pred.left==p);
					   if(isLeft) pred.left=null; //if it is on the 'pred's' left, delete 'pred's' left
					   else pred.right=null; //else, delete 'pred's' right
				   }
			   }else if((p.left!=null)&&p.right==null) { //if node 'p' has a left successor
				   if(pred==null) 
					   c=p.left;
				   else {
				   btNode temp = p.left; //declare btNode variable 'temp' that points to 'p's' left successor
				   isLeft = (pred.left==p);
				   if(isLeft) pred.left=temp; //if it is inn 'pred's' left, point 'pred's' left to 'temp' variable
				   else pred.right=temp; //else, point 'pred's' right to 'temp' variable
				   p.left=null; //delete 'p's' left successor
				   }
			   }else if((p.left==null)&&p.right!=null) { //if node 'p' has a right successor
				   if(pred==null) 
					   c=p.right;
				   else {
				   btNode temp = p.right;
				   isLeft = (pred.left==p);
				   if(isLeft) pred.left=temp;
				   else pred.right=temp;
				   p.right=null;
				   }
			   }else {
				   if(pred==null)
					   c=replacement(p);
				   else {
				   btNode r=replacement(p); //declare temporary btNode variable 'r' that points to a given replacement variable
				   isLeft = (pred.left==p);
				   if(isLeft) pred.left=r;
				   else pred.right=r;
			   }
			   }
			   howmany--; //decrease counting variable
		   }
	   }

	 /*return the number of elements in the binary tree c*/
	   public int get_howmany()
	   {
	      	return howmany;
	   }

	   //call the print method of binary tree 'c'
	   public void print()
	   {
		   //if(this.c==null) System.out.println("empty");
		   printtree(c);
	   }

	   //In the equals method, convert two binary trees to arrays and compare the content of the arrays
	   public boolean equals(Intcoll6 obj)
	   {
		   int j=0; boolean result=(howmany==obj.howmany);
		   
		   if(result) {
			   int x[]=new int[howmany], y[]=new int[howmany]; //declare two arrays
			   toarray(c, x, 0); toarray(obj.c, y, 0); //call 'toarray' method
			   
			   while((result)&&(j<howmany)) { 
				   result=(x[j]==y[j]); j++;
			   }
		   }
		   return result;
	   }
	   
	   //print the content of the binary tree using the in-order algorithm
	   private static void printtree(btNode t) {
		   if(t!=null) {
		   printtree(t.left); //start at left subtree
		   System.out.println(t.info); //go the root node
		   printtree(t.right); //finish at right subtree
		   }
		   return;
	   }
	   
	   private static int toarray(btNode t, int []a, int i) {
		   int num_nodes=0;
		   
		   if(t!=null) {
			   num_nodes=toarray(t.left, a, i);
			   a[num_nodes+i]=t.info;
			   num_nodes=num_nodes+1+toarray(t.right, a, num_nodes+i+1);
		   }
		   return num_nodes;
	   }
	   
	   private static btNode replacement(btNode t) {
		   btNode pred=t, p=t, next=t.right;
		   
		   while(next!=null) {
			   pred=p;
			   p=next;
			   next=next.left;
		   }
		   
		   if(p!=t.right) {
			   btNode temp =p.right;
			   p.right=t.right;
			   p.left=t.left;
			   pred.left=temp;
		   }
		   
		   return p;
	   }
	   
	   //btNode class
	   private static class btNode{
			int info; btNode left, right;
			
			private btNode(int s, btNode lt, btNode rt) {
				info=s; left=lt; right=rt;
			}
			private btNode() {
				this.info=info; left=null; right=null;
			}
		}
}



