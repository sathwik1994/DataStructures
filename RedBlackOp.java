import java.io.File;
import java.util.Scanner;

public class RedBlackOp {
    public static String new_line;
     private final int RED = 0;
     private final static int BLACK = 1;
     //private Node root;     // root of the BST

     static private class RedBlackNode {

            int nodeValue = -1;
            int nodeColor = BLACK;
            RedBlackNode nodeLeft = noValue;
            RedBlackNode nodeRight = noValue;
            RedBlackNode nodeParent = noValue;

            RedBlackNode(int value) {
                this.nodeValue = value;
            }
        }
      private final static RedBlackNode noValue = new RedBlackNode(-1);
        private static RedBlackNode head = noValue;
       
        private RedBlackNode search(RedBlackNode searchnode, RedBlackNode compare) {
            if (head == noValue) {
                return null;
            }

            if (searchnode.nodeValue < compare.nodeValue) {
                if (compare.nodeLeft != noValue) {
                    return search(searchnode, compare.nodeLeft);
                }
            } else if (searchnode.nodeValue > compare.nodeValue) {
                if (compare.nodeRight != noValue) {
                    return search(searchnode, compare.nodeRight);
                }
            } else if (searchnode.nodeValue == compare.nodeValue) {
                return compare;
            }
            return null;
        }
        private void insertElement(RedBlackNode rbnode) {
            RedBlackNode t = head;
            if (head == noValue) {
                head = rbnode;
                rbnode.nodeColor = BLACK;
                rbnode.nodeParent = noValue;
            } else {
                rbnode.nodeColor = RED;
                while (true) {
                    if (rbnode.nodeValue < t.nodeValue) {
                        if (t.nodeLeft == noValue) {
                            t.nodeLeft = rbnode;
                            rbnode.nodeParent = t;
                            break;
                        } else {
                            t = t.nodeLeft;
                        }
                    } else if (rbnode.nodeValue >= t.nodeValue) {
                        if (t.nodeRight == noValue) {
                            t.nodeRight = rbnode;
                            rbnode.nodeParent = t;
                            break;
                        } else {
                            t = t.nodeRight;
                        }
                    }
                }
               
                while (rbnode.nodeParent.nodeColor == RED) {
                    RedBlackNode nodeUncle = noValue;
                    if (rbnode.nodeParent == rbnode.nodeParent.nodeParent.nodeLeft) {
                        nodeUncle = rbnode.nodeParent.nodeParent.nodeRight;

                        if (nodeUncle != noValue && nodeUncle.nodeColor == RED) {
                            rbnode.nodeParent.nodeColor = BLACK;
                            nodeUncle.nodeColor = BLACK;
                            rbnode.nodeParent.nodeParent.nodeColor = RED;
                            rbnode = rbnode.nodeParent.nodeParent;
                            continue;
                        }
                        if (rbnode == rbnode.nodeParent.nodeRight) {
                           
                            rbnode = rbnode.nodeParent;
                            leftRotate(rbnode);
                        }
                        rbnode.nodeParent.nodeColor = BLACK;
                        rbnode.nodeParent.nodeParent.nodeColor = RED;
                       
                        rotateRight(rbnode.nodeParent.nodeParent);
                    } else {
                        nodeUncle = rbnode.nodeParent.nodeParent.nodeLeft;
                         if (nodeUncle != noValue && nodeUncle.nodeColor == RED) {
                            rbnode.nodeParent.nodeColor = BLACK;
                            nodeUncle.nodeColor = BLACK;
                            rbnode.nodeParent.nodeParent.nodeColor = RED;
                            rbnode = rbnode.nodeParent.nodeParent;
                            continue;
                        }
                        if (rbnode == rbnode.nodeParent.nodeLeft) {
                            //Double rotation needed
                            rbnode = rbnode.nodeParent;
                            rotateRight(rbnode);
                        }
                        rbnode.nodeParent.nodeColor = BLACK;
                        rbnode.nodeParent.nodeParent.nodeColor = RED;
                    
                        leftRotate(rbnode.nodeParent.nodeParent);
                    }
                }
                head.nodeColor = BLACK;
            }
        }


        void leftRotate(RedBlackNode node) {
            if (node.nodeParent != noValue) {
                if (node == node.nodeParent.nodeLeft) {
                    node.nodeParent.nodeLeft = node.nodeRight;
                } else {
                    node.nodeParent.nodeRight = node.nodeRight;
                }
                node.nodeRight.nodeParent = node.nodeParent;
                node.nodeParent = node.nodeRight;
                if (node.nodeRight.nodeLeft != noValue) {
                    node.nodeRight.nodeLeft.nodeParent = node;
                }
                node.nodeRight = node.nodeRight.nodeLeft;
                node.nodeParent.nodeLeft = node;
            } else {//Need to rotate root
                RedBlackNode childRight = head.nodeRight;
                head.nodeRight = childRight.nodeLeft;
                childRight.nodeLeft.nodeParent = head;
                head.nodeParent = childRight;
                childRight.nodeLeft = head;
                childRight.nodeParent = noValue;
                head = childRight;
            }
        }

        void rotateRight(RedBlackNode rbnode) {
            if (rbnode.nodeParent != noValue) {
                if (rbnode == rbnode.nodeParent.nodeLeft) {
                    rbnode.nodeParent.nodeLeft = rbnode.nodeLeft;
                } else {
                    rbnode.nodeParent.nodeRight = rbnode.nodeLeft;
                }

                rbnode.nodeLeft.nodeParent = rbnode.nodeParent;
                rbnode.nodeParent = rbnode.nodeLeft;
                if (rbnode.nodeLeft.nodeRight != noValue) {
                    rbnode.nodeLeft.nodeRight.nodeParent = rbnode;
                }
                rbnode.nodeLeft = rbnode.nodeLeft.nodeRight;
                rbnode.nodeParent.nodeRight = rbnode;
            } else {//Need to rotate root
                RedBlackNode childLeft = head.nodeLeft;
                head.nodeLeft = head.nodeLeft.nodeRight;
                childLeft.nodeRight.nodeParent = head;
                head.nodeParent = childLeft;
                childLeft.nodeRight = head;
                childLeft.nodeParent = noValue;
                head = childLeft;
            }
        }
        void transplant(RedBlackNode target, RedBlackNode source){
              if(target.nodeParent == noValue){
                  head = source;
              }else if(target == target.nodeParent.nodeLeft){
                  target.nodeParent.nodeLeft = source;
              }else
                  target.nodeParent.nodeRight = source;
              source.nodeParent = target.nodeParent;
        }
       
        boolean deleteElement(RedBlackNode c){
            if((c = search(c, head))==null)return false;
            RedBlackNode a;
            RedBlackNode b = c; // temporary reference y
            int b_original_color = b.nodeColor;
           
            if(c.nodeLeft == noValue){
                a = c.nodeRight; 
                transplant(c, c.nodeRight); 
            }else if(c.nodeRight == noValue){
                a = c.nodeLeft;
                transplant(c, c.nodeLeft);
            }else{
                b = minTree(c.nodeRight);
                b_original_color = b.nodeColor;
                a = b.nodeRight;
                if(b.nodeParent == c)
                    a.nodeParent = b;
                else{
                    transplant(b, b.nodeRight);
                    b.nodeRight = c.nodeRight;
                    b.nodeRight.nodeParent = b;
                }
                transplant(c, b);
                b.nodeLeft = c.nodeLeft;
                b.nodeLeft.nodeParent = b;
                b.nodeColor = c.nodeColor;
            }
            if(b_original_color==BLACK){
                //deleteFixup(x); 
                while(a!=head && a.nodeColor == BLACK){
                    if(a == a.nodeParent.nodeLeft){
                        RedBlackNode d = a.nodeParent.nodeRight;
                        if(d.nodeColor == RED){
                            d.nodeColor = BLACK;
                            a.nodeParent.nodeColor = RED;
                            leftRotate(a.nodeParent);
                            d = a.nodeParent.nodeRight;
                        }
                        if(d.nodeLeft.nodeColor == BLACK && d.nodeRight.nodeColor == BLACK){
                            d.nodeColor = RED;
                            a = a.nodeParent;
                            continue;
                        }
                        else if(d.nodeRight.nodeColor == BLACK){
                            d.nodeLeft.nodeColor = BLACK;
                            d.nodeColor = RED;
                            rotateRight(d);
                            d = a.nodeParent.nodeRight;
                        }
                        if(d.nodeRight.nodeColor == RED){
                            d.nodeColor = a.nodeParent.nodeColor;
                            a.nodeParent.nodeColor = BLACK;
                            d.nodeRight.nodeColor = BLACK;
                            leftRotate(a.nodeParent);
                            a = head;
                        }
                    }else{
                        RedBlackNode d = a.nodeParent.nodeLeft;
                        if(d.nodeColor == RED){
                            d.nodeColor = BLACK;
                            a.nodeParent.nodeColor = RED;
                            rotateRight(a.nodeParent);
                            d = a.nodeParent.nodeLeft;
                        }
                        if(d.nodeRight.nodeColor == BLACK && d.nodeLeft.nodeColor == BLACK){
                            d.nodeColor = RED;
                            a = a.nodeParent;
                            continue;
                        }
                        else if(d.nodeLeft.nodeColor == BLACK){
                            d.nodeRight.nodeColor = BLACK;
                            d.nodeColor = RED;
                            leftRotate(d);
                            d = a.nodeParent.nodeLeft;
                        }
                        if(d.nodeLeft.nodeColor == RED){
                            d.nodeColor = a.nodeParent.nodeColor;
                            a.nodeParent.nodeColor = BLACK;
                            d.nodeLeft.nodeColor = BLACK;
                            rotateRight(a.nodeParent);
                            a = head;
                        }
                    }
                }
                a.nodeColor = BLACK;
            }
            return true;
        }
       
      
       
        RedBlackNode minTree(RedBlackNode nextRoot){
            while(nextRoot.nodeLeft!=noValue){
                nextRoot = nextRoot.nodeLeft;
            }
            return nextRoot;
        }
        public void inOrderDisplay(RedBlackNode node) {
            String Color;
            if (node == noValue) {
                return;
            }
           
            inOrderDisplay(node.nodeLeft);
            if(node.nodeColor==1){
                Color="Black";
            }
            else{
                Color="Red";
            }
            System.out.print(node.nodeValue+Color+ " ");
           
           
            //System.out.print(((node.nodeColor==RED)?"Color: Red ":"Color: Black ")+"Key: "+node.nodeValue+" Parent: "+node.nodeParent.nodeValue+"\n");
            inOrderDisplay(node.nodeRight);
        }

       


    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try{
            System.out.println("Enter file name: ");
            Scanner inpt = new Scanner(System.in);

            File inpt_fil = new File(inpt.nextLine());
            inpt = new Scanner(inpt_fil);
            while (inpt.hasNextLine()) {
                new_line = inpt.nextLine();
                System.out.println("List before operation:"+new_line);
               
            }
             inpt.close();
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
       
             String[] splited = new_line.split("\\s+");
                //int n= Integer.parseInt(splited[0]);
                 RedBlackOp rb= new RedBlackOp();
                
               
                try{
                    for(int i=0; i<splited.length;i++){
                    if(splited[i].endsWith(".in")){
                       
                        RedBlackNode node = new RedBlackNode(Integer.parseInt(splited[i].substring(0,splited[i].indexOf("."))));
                       
                        if(rb.search(node, head)==null){
                            System.out.println();
                            System.out.print("Before Insertion Opeartion:");
                            rb.inOrderDisplay(head);
                           
                            rb.insertElement(node);
                            System.out.println();
                            System.out.print("After Insertion Opeartion:");
                           
                            rb.inOrderDisplay(head);
                           
                           
                        }
                        else{
                            System.out.println("Duplicate key");
                        }
                    }
                   
                   
                    else if(splited[i].endsWith(".del")){
                        RedBlackNode node = new RedBlackNode(Integer.parseInt(splited[i].substring(0,splited[i].indexOf("."))));
                        System.out.println();
                        System.out.print("Before deletion Opeartion:");
                        rb.inOrderDisplay(head);
                        System.out.println();
                        rb.deleteElement(node);
                        System.out.print("After deletion Opeartion:  ");
                        rb.inOrderDisplay(head);
                    }
               
                    else{
                        System.out.println("invalid key");
                    }
                                   
                }
                    System.out.println();
                    System.out.println("Final Tree");
                    rb.inOrderDisplay(head); }
   
                   catch(Exception ex){
                       System.out.println("invalid key");
                   }
    }
                  
              

}