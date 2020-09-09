//package hw4;

public class TNode<E>{
	private E element;
	private TNode<E> parent;     // reference to next node in list

	/** Constructor
	* @param item the element to be stored in Node
	* @param nextVal the next Node that this is pointing to
	*/
	public TNode(E item, TNode<E> parentValue){ 
		element = item;  
		parent = parentValue; 
	}
	/** Constructor
	* @param item the element to be stored in Node
	*/
	public TNode(E item){
		element = item;
		parent = null;
	}
	//other constructors
	public TNode(){
		element = null;
		parent = null;
	}
	
	public TNode(TNode<E> parentValue) { 
		parent = parentValue; 
	}
 
	/**
	 *@return the Node that is next to this
	 */
	public TNode<E> getParent() { 
		return parent; 
	}  
	/**
	 * Sets this next to Node(){
		element = null;
		parenthe given Node
	 * @param nextNal the Node that is to be set to this Node's next
	 */
	public void setParent(TNode<E> parentValue){
		parent = parentValue; 
	}     
	
	/** 
	 * returns the element in the Node
	 *@return element in the Node
	 */  
	public E getElement() { 
		return element; 
	}  
	
	/**
	 * sets the element stored in Node to the element given
	 *@param item the element to be stored in Node.
	 */
	public E setElement(E item) {
		return element = item; 
	}

}