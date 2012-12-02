

//class that runs an specific method on the process_object member with the specified
//delay occuring afterward
//A "method" object
abstract class THProcess{
	
	protected Object process_object;

	public THProcess(Object process_object){
		this.process_object = process_object;
	}
    
    public void setObject(Object new_process_object){
        process_object = new_process_object;
        return;
    }
    
    public Object getObject(){
        return process_object;
    }
	
	//runs the current process by calling the coded method on process_object
	abstract void run();
}
