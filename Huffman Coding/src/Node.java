
public class Node implements Comparable<Node> {
    private int frequency;
    private int curFrequency;
    private String character;
    private Node leftNode;
    private Node rightNode;

    public Node(String character, int frequency, Node leftNode, Node rightNode)
    {
        this.leftNode = leftNode;
        this.rightNode = rightNode;
        this.curFrequency = frequency;
        this.character = character;
        if(leftNode == null && rightNode == null){
            this.frequency = this.curFrequency;
        }
        else{
            this.frequency = this.leftNode.getFrequency() + this.rightNode.getFrequency();
        }

    }

    public void setFrequency( int frequency ){
        this.frequency = frequency ;
    }
    public Node getLeftNode(){
        return leftNode;
    }
    public Node getRightNode(){
        return rightNode;
    }

    public int getFrequency() {
        return frequency;
    }
    public String getString(){
        return character;
    }
    @Override
    public int compareTo(Node node)
    {
        return Integer.compare(frequency, node.getFrequency());
    }
}
