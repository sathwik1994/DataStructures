import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class RedBlackTreeImp {
	private final int RED = 0;
	private final int BLACK = 1;
		class RBTNode{
		int value = -1;
		int color = BLACK;
		RBTNode childLeft = novalue;
		RBTNode childRight = novalue;
		RBTNode parent = novalue;		
		RBTNode(int value){
			this.value = value;			
		}		
	}
	private final RBTNode novalue = new RBTNode(-1); 
    private RBTNode root = novalue;
    public void inOrder(RBTNode node) {
        if (node == novalue) {
            return;
        }        
        inOrder(node.childLeft);
        System.out.print(node.value+((node.color==RED)?"R":"B")+ " ");
        inOrder(node.childRight);
    }
    public void insertValue(RBTNode node){
    	if((find(node, root))!=null)
    		{System.out.println("\nduplicate key");return;}
    	RBTNode temp = root;
    	if(root == novalue){
    		root = node;
    		node.color = BLACK;
    		node.parent = novalue;
    		return;
    	}else{
    		node.color = RED;
    		while(true){
    			if(node.value < temp.value){
    				if(temp.childLeft == novalue){
    					temp.childLeft = node;
    					node.parent = temp;
    					break;
    				}else{
    					temp = temp.childLeft;
    				}
    			}
    			if(node.value > temp.value){
    				if(temp.childRight == novalue){
    					temp.childRight = node;
    					node.parent = temp;
    					break;
    				}else{
    					temp = temp.childRight;
    				}
    			}
    		}
    		fixUpInsert(node);
    	}
    }
    public void fixUpInsert(RBTNode node){
    	while(node.parent.color == RED){
    		RBTNode uncle = novalue;
    		if(node.parent == node.parent.parent.childLeft){
    			uncle = node.parent.parent.childRight;
    			if(uncle!= novalue && uncle.color == RED){
    				
    				node.parent.color = BLACK;
    				node.parent.parent.color = RED;
    				uncle.color = BLACK;
    				node = node.parent.parent;
    				continue; 				
    			}
    			if(node == node.parent.childRight){
    				node = node.parent;
    				leftRotate(node);
    			}
    			coloursSwap(node);
    			rightRotate(node.parent.parent);
    			
    		}else{
    			uncle = node.parent.parent.childLeft;
    			if(uncle!= novalue && uncle.color == RED){
    				
    				node.parent.color = BLACK;
    				node.parent.parent.color = RED;
    				uncle.color = BLACK;
    				node = node.parent.parent;
    				continue; 				
    			}
    			if(node == node.parent.childLeft){
    				node = node.parent;
    				rightRotate(node);
    			}
    			coloursSwap(node);
    			leftRotate(node.parent.parent);    			
    		}
    	}
    	root.color = BLACK;   	
    }
    void coloursSwap(RBTNode node){
    	int tempColor;
    	tempColor = node.parent.color;
    	node.parent.color = node.parent.parent.color;
    	node.parent.parent.color = tempColor;
    }
    void leftRotate(RBTNode node){
    	if(node.parent != novalue){
    		
    		if(node ==node.parent.childLeft){
    			node.parent.childLeft = node.childRight;
    		}else{
    			
    			node.parent.childRight = node.childRight;
    		}
    		node.childRight.parent = node.parent;
    		node.parent = node.childRight;
    		if(node.childRight.childLeft != novalue){
    			node.childRight.childLeft.parent = node;
    		}
    		node.childRight=node.childRight.childLeft;
    		node.parent.childLeft = node;
    	}else{
    		//right right child rotate
    		RBTNode childRight = root.childRight;
    		root.childRight = childRight.childLeft;
    		childRight.childLeft.parent = root;
    		root.parent = childRight;
    		childRight.childLeft = root;
    		childRight.parent = novalue;
    		root = childRight;
    	}  	
    }
    void rightRotate(RBTNode node){   	
    	if(node.parent != novalue){    	
    		if (node == node.parent.childLeft) {
                node.parent.childLeft = node.childLeft;
            } else {
                node.parent.childRight = node.childLeft;
            }
            node.childLeft.parent = node.parent;
            node.parent = node.childLeft;
            if (node.childLeft.childRight != novalue) {
                node.childLeft.childRight.parent = node;
            }
            node.childLeft = node.childLeft.childRight;
            node.parent.childRight = node;
    	}else{
    		//left left child rotate
    		RBTNode childLeft = root.childLeft;
    		root.childLeft = childLeft.childRight;
    		childLeft.childRight.parent = root;
    		root.parent = childLeft;
    		childLeft.childLeft = root;
    		childLeft.parent = novalue;
    		root = childLeft;   		 		
    	}  	
    }
    private RBTNode find(RBTNode findNode, RBTNode node) {
        if (root == novalue) {
            return null;
        }
        if (findNode.value < node.value) {
            if (node.childLeft != novalue) {
                return find(findNode, node.childLeft);
            }
        } else if (findNode.value > node.value) {
            if (node.childRight != novalue) {
                return find(findNode, node.childRight);
            }
        } else if (findNode.value == node.value) {
            return node;
        }
        return null;
    }
    boolean deleteValue(RBTNode z){
        if((z = find(z, root))==null)return false;
        RBTNode x;
        RBTNode y = z; // temporary reference y
        int y_original_color = y.color;
        
        if(z.childLeft == novalue){
            x = z.childRight;  
            transplant(z, z.childRight);  
        }else if(z.childRight == novalue){
            x = z.childLeft;
            transplant(z, z.childLeft); 
        }else{
            y = treeMinimum(z.childRight);
            y_original_color = y.color;
            x = y.childRight;
            if(y.parent == z)
                x.parent = y;
            else{
                transplant(y, y.childRight);
                y.childRight = z.childRight;
                y.childRight.parent = y;
            }
            transplant(z, y);
            y.childLeft = z.childLeft;
            y.childLeft.parent = y;
            y.color = z.color; 
        }
        if(y_original_color==BLACK)
            fixUpDelete(x);  
        return true;
    }
    RBTNode treeMinimum(RBTNode subTreeRoot){
        while(subTreeRoot.childLeft!=novalue){
            subTreeRoot = subTreeRoot.childLeft;
        }
        return subTreeRoot;
    }
void fixUpDelete(RBTNode fixUpNode){
        while(fixUpNode!=root && fixUpNode.color == BLACK){
            if(fixUpNode == fixUpNode.parent.childLeft){
                RBTNode node = fixUpNode.parent.childRight;
                if(node.color == RED){
                    node.color = BLACK;
                    fixUpNode.parent.color = RED;
                    leftRotate(fixUpNode.parent);
                    node = fixUpNode.parent.childRight;
                }
                if(node.childLeft.color == BLACK && node.childRight.color == BLACK){
                    node.color = RED;
                    fixUpNode = fixUpNode.parent;
                    continue;
                }
                else if(node.childRight.color == BLACK){
                    node.childLeft.color = BLACK;
                    node.color = RED;
                    rightRotate(node);
                    node = fixUpNode.parent.childRight;
                }
                if(node.childRight.color == RED){
                    node.color = fixUpNode.parent.color;
                    fixUpNode.parent.color = BLACK;
                    node.childRight.color = BLACK;
                    leftRotate(fixUpNode.parent);
                    fixUpNode = root;
                }
            }else{
                RBTNode node = fixUpNode.parent.childLeft;
                if(node.color == RED){
                    node.color = BLACK;
                    fixUpNode.parent.color = RED;
                    rightRotate(fixUpNode.parent);
                    node = fixUpNode.parent.childLeft;
                }
                if(node.childRight.color == BLACK && node.childLeft.color == BLACK){
                    node.color = RED;
                    fixUpNode = fixUpNode.parent;
                    continue;
                }
                else if(node.childLeft.color == BLACK){
                    node.childRight.color = BLACK;
                    node.color = RED;
                    leftRotate(node);
                    node = fixUpNode.parent.childLeft;
                }
                if(node.childLeft.color == RED){
                    node.color = fixUpNode.parent.color;
                    fixUpNode.parent.color = BLACK;
                    node.childLeft.color = BLACK;
		    rightRotate(fixUpNode.parent);
                    fixUpNode = root;
                }
            }
        }
        fixUpNode.color = BLACK;
    }
	void transplant(RBTNode node1, RBTNode node2){
        if(node1.parent == novalue){
            root = node2;
        }else if(node1 == node1.parent.childLeft){
            node1.parent.childLeft = node2;
        }else
            node1.parent.childRight = node2;
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
    	            				insertValue(new RBTNode(Integer.parseInt(individualEntries[0])));
    	            				System.out.println();
    	            				System.out.println("Display the tree after the operation (in-order):");
    	            				inOrder(root);
    	            				System.out.println();
    	            				}else if(individualEntry.equals("del")){
    	            					System.out.println("Display the tree before (in-order):");
        	            				inOrder(root);	
    	            				deleteValue(new RBTNode(Integer.parseInt(individualEntries[0])));
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
