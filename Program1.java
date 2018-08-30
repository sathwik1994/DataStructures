import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Program1 {
	private final int RED = 0;
	private final int BLACK = 1;
		class RBNode{
		int data = -1;
		int color = BLACK;
		RBNode leftChild = nil;
		RBNode rightChild = nil;
		RBNode parent = nil;		
		RBNode(int data){
			this.data = data;			
		}		
	}
	private final RBNode nil = new RBNode(-1); 
    private RBNode root = nil;
    public void inOrder(RBNode node) {
        if (node == nil) {
            return;
        }        
        inOrder(node.leftChild);
        System.out.print(node.data+((node.color==RED)?"R":"B")+ " ");
        inOrder(node.rightChild);
    }
    public void insert(RBNode node){
    	if((find(node, root))!=null)
    		{System.out.println("\nduplicate key");return;}
    	RBNode temp = root;
    	if(root == nil){
    		root = node;
    		node.color = BLACK;
    		node.parent = nil;
    		return;
    	}else{
    		node.color = RED;
    		while(true){
    			if(node.data < temp.data){
    				if(temp.leftChild == nil){
    					temp.leftChild = node;
    					node.parent = temp;
    					break;
    				}else{
    					temp = temp.leftChild;
    				}
    			}
    			if(node.data > temp.data){
    				if(temp.rightChild == nil){
    					temp.rightChild = node;
    					node.parent = temp;
    					break;
    				}else{
    					temp = temp.rightChild;
    				}
    			}
    		}
    		insertFixUp(node);
    	}
    }
    public void insertFixUp(RBNode node){
    	while(node.parent.color == RED){
    		RBNode uncle = nil;
    		if(node.parent == node.parent.parent.leftChild){
    			uncle = node.parent.parent.rightChild;
    			if(uncle!= nil && uncle.color == RED){
    				
    				node.parent.color = BLACK;
    				node.parent.parent.color = RED;
    				uncle.color = BLACK;
    				node = node.parent.parent;
    				continue; 				
    			}
    			if(node == node.parent.rightChild){
    				node = node.parent;
    				rotateLeft(node);
    			}
    			swapColors(node);
    			rotateRight(node.parent.parent);
    			
    		}else{
    			uncle = node.parent.parent.leftChild;
    			if(uncle!= nil && uncle.color == RED){
    				
    				node.parent.color = BLACK;
    				node.parent.parent.color = RED;
    				uncle.color = BLACK;
    				node = node.parent.parent;
    				continue; 				
    			}
    			if(node == node.parent.leftChild){
    				node = node.parent;
    				rotateRight(node);
    			}
    			swapColors(node);
    			rotateLeft(node.parent.parent);    			
    		}
    	}
    	root.color = BLACK;   	
    }
    void swapColors(RBNode node){
    	int tempColor;
    	tempColor = node.parent.color;
    	node.parent.color = node.parent.parent.color;
    	node.parent.parent.color = tempColor;
    }
    void rotateLeft(RBNode node){
    	if(node.parent != nil){
    		
    		if(node ==node.parent.leftChild){
    			node.parent.leftChild = node.rightChild;
    		}else{
    			
    			node.parent.rightChild = node.rightChild;
    		}
    		node.rightChild.parent = node.parent;
    		node.parent = node.rightChild;
    		if(node.rightChild.leftChild != nil){
    			node.rightChild.leftChild.parent = node;
    		}
    		node.rightChild=node.rightChild.leftChild;
    		node.parent.leftChild = node;
    	}else{
    		//right right child rotate
    		RBNode rightChild = root.rightChild;
    		root.rightChild = rightChild.leftChild;
    		rightChild.leftChild.parent = root;
    		root.parent = rightChild;
    		rightChild.leftChild = root;
    		rightChild.parent = nil;
    		root = rightChild;
    	}  	
    }
    void rotateRight(RBNode node){   	
    	if(node.parent != nil){    	
    		if (node == node.parent.leftChild) {
                node.parent.leftChild = node.leftChild;
            } else {
                node.parent.rightChild = node.leftChild;
            }
            node.leftChild.parent = node.parent;
            node.parent = node.leftChild;
            if (node.leftChild.rightChild != nil) {
                node.leftChild.rightChild.parent = node;
            }
            node.leftChild = node.leftChild.rightChild;
            node.parent.rightChild = node;
    	}else{
    		//left left child rotate
    		RBNode leftChild = root.leftChild;
    		root.leftChild = leftChild.rightChild;
    		leftChild.rightChild.parent = root;
    		root.parent = leftChild;
    		leftChild.leftChild = root;
    		leftChild.parent = nil;
    		root = leftChild;   		 		
    	}  	
    }
    private RBNode find(RBNode findNode, RBNode node) {
        if (root == nil) {
            return null;
        }
        if (findNode.data < node.data) {
            if (node.leftChild != nil) {
                return find(findNode, node.leftChild);
            }
        } else if (findNode.data > node.data) {
            if (node.rightChild != nil) {
                return find(findNode, node.rightChild);
            }
        } else if (findNode.data == node.data) {
            return node;
        }
        return null;
    }
    boolean delete(RBNode z){
        if((z = find(z, root))==null)return false;
        RBNode x;
        RBNode y = z; // temporary reference y
        int y_original_color = y.color;
        
        if(z.leftChild == nil){
            x = z.rightChild;  
            transplant(z, z.rightChild);  
        }else if(z.rightChild == nil){
            x = z.leftChild;
            transplant(z, z.leftChild); 
        }else{
            y = treeMinimum(z.rightChild);
            y_original_color = y.color;
            x = y.rightChild;
            if(y.parent == z)
                x.parent = y;
            else{
                transplant(y, y.rightChild);
                y.rightChild = z.rightChild;
                y.rightChild.parent = y;
            }
            transplant(z, y);
            y.leftChild = z.leftChild;
            y.leftChild.parent = y;
            y.color = z.color; 
        }
        if(y_original_color==BLACK)
            deleteFixup(x);  
        return true;
    }
    RBNode treeMinimum(RBNode subTreeRoot){
        while(subTreeRoot.leftChild!=nil){
            subTreeRoot = subTreeRoot.leftChild;
        }
        return subTreeRoot;
    }
void deleteFixup(RBNode fixUpNode){
        while(fixUpNode!=root && fixUpNode.color == BLACK){
            if(fixUpNode == fixUpNode.parent.leftChild){
                RBNode node = fixUpNode.parent.rightChild;
                if(node.color == RED){
                    node.color = BLACK;
                    fixUpNode.parent.color = RED;
                    rotateLeft(fixUpNode.parent);
                    node = fixUpNode.parent.rightChild;
                }
                if(node.leftChild.color == BLACK && node.rightChild.color == BLACK){
                    node.color = RED;
                    fixUpNode = fixUpNode.parent;
                    continue;
                }
                else if(node.rightChild.color == BLACK){
                    node.leftChild.color = BLACK;
                    node.color = RED;
                    rotateRight(node);
                    node = fixUpNode.parent.rightChild;
                }
                if(node.rightChild.color == RED){
                    node.color = fixUpNode.parent.color;
                    fixUpNode.parent.color = BLACK;
                    node.rightChild.color = BLACK;
                    rotateLeft(fixUpNode.parent);
                    fixUpNode = root;
                }
            }else{
                RBNode node = fixUpNode.parent.leftChild;
                if(node.color == RED){
                    node.color = BLACK;
                    fixUpNode.parent.color = RED;
                    rotateRight(fixUpNode.parent);
                    node = fixUpNode.parent.leftChild;
                }
                if(node.rightChild.color == BLACK && node.leftChild.color == BLACK){
                    node.color = RED;
                    fixUpNode = fixUpNode.parent;
                    continue;
                }
                else if(node.leftChild.color == BLACK){
                    node.rightChild.color = BLACK;
                    node.color = RED;
                    rotateLeft(node);
                    node = fixUpNode.parent.leftChild;
                }
                if(node.leftChild.color == RED){
                    node.color = fixUpNode.parent.color;
                    fixUpNode.parent.color = BLACK;
                    node.leftChild.color = BLACK;
		    rotateRight(fixUpNode.parent);
                    fixUpNode = root;
                }
            }
        }
        fixUpNode.color = BLACK;
    }
	void transplant(RBNode node1, RBNode node2){
        if(node1.parent == nil){
            root = node2;
        }else if(node1 == node1.parent.leftChild){
            node1.parent.leftChild = node2;
        }else
            node1.parent.rightChild = node2;
        node2.parent = node1.parent;
  }
	    public void readFile() throws FileNotFoundException{
    		System.out.println("Enter your input file name:");
    	   	 Scanner fileName = new Scanner(System.in);   	   	
		 String file = fileName.nextLine();
		 File locationOfInputFile = new File(file); 
    	   	 //File locationOfInputFile = new File("D:\\Workspace\\Data Structures\\src\\com\\DS\\input1.txt");
    	   	 Scanner inputFile = new Scanner(locationOfInputFile);               
    	        String [] inputArgs = inputFile.nextLine().split(" ");  
    	        for(String inputArg: inputArgs){
    	            String [] individualEntries = inputArg.split("\\.");
    	            		int j=0;
    	                    for(String individualEntry : individualEntries){
    	            			if(j==1){
    	            				if(individualEntry.equals("in")){
    	            					System.out.println("Display the tree before (in-order):");
        	            				inOrder(root);	
    	            				insert(new RBNode(Integer.parseInt(individualEntries[0])));
    	            				System.out.println();
    	            				System.out.println("Display the tree after the operation (in-order):");
    	            				inOrder(root);
    	            				System.out.println();
    	            				}else if(individualEntry.equals("del")){
    	            					System.out.println("Display the tree before (in-order):");
        	            				inOrder(root);	
    	            				delete(new RBNode(Integer.parseInt(individualEntries[0])));
    	            				System.out.println();
    	            				System.out.println("Display the tree after the operation (in-order):");
    	            				inOrder(root);
    	            				System.out.println();
    	            				}
    	            			}      			
    	            			j++;
    	                    }
    	                    inputFile.close();
    	                    fileName.close();
    	            }
    	            }
    	public static void main(String[] args) throws FileNotFoundException{
        	Program1 rbt = new Program1();
        	rbt.readFile();
    }   
}
