
import java.util.ArrayDeque;
import java.awt.event.*;
import javax.swing.*;

//Class manages a queue of processes and delays to run in the future
public class THProcessScheduler implements ActionListener{
	
	protected Timer process_timer;
	protected ArrayDeque<THProcess> process_queue;
	protected ArrayDeque<Integer> delay_queue;
    protected int delay;
    
    public static int default_timer_interval = 1; //sets the default interval of the timer to
                                                  //1 ms, the smallest interval available with timers
                                                  //for maximum precision

	public THProcessScheduler(){
		this(new Timer(default_timer_interval, null));
    }

	public THProcessScheduler(Timer external_timer){
		process_timer = external_timer;
		process_queue = new ArrayDeque<THProcess>();
		delay_queue = new ArrayDeque<Integer>();
        delay = 0;
		
		process_timer.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e){
		
		if(!process_queue.isEmpty()){
			if(delay == 0){
				process_queue.remove().run();
                delay = delay_queue.remove().intValue();
			}
			else{
				delay--;
			}
		}
        else if(delay > 0){
            delay--;
        }
	}

	public void add(THProcess new_process, int delay){
		process_queue.add(new_process);
        delay_queue.add(new Integer(delay));
	}
    
    public void addTimerListener(Object new_listener){
        process_timer.addActionListener((ActionListener)new_listener);
    }

	public void start(){
		process_timer.start();
	}

	public void halt(){
		process_timer.stop();
	}
	
	public boolean isActive(){
		if(!process_queue.isEmpty() || delay != 0){
				return true;
		}
		
		return false;
	}
	
	public boolean processesQueued(){
		return !process_queue.isEmpty();
	}
}

