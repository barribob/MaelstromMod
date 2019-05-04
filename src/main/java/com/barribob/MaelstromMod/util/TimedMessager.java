package com.barribob.MaelstromMod.util;

import java.util.function.Consumer;
import java.util.function.Function;

import net.minecraft.world.World;

/**
 * Sends timed messages using the updates of another class
 */
public class TimedMessager
{
    private final String[] messages;
    private final int[] message_times;
    private int ticksExisted = 0;
    private int message = 0;
    private Consumer<String> onMessageEnd;
    private boolean calledMessage = false;
    
    public TimedMessager(String[] messages, int[] message_times, Consumer<String> onMessageEnd)
    {
	this.message_times = message_times;
	this.messages = messages;
	this.onMessageEnd = onMessageEnd;
	if(this.message_times.length != this.messages.length)
	{
	    throw new IllegalArgumentException("Message times and messages lengths do not match");
	}
    }
    
    public void Update(World world, Consumer<String> message_func)
    {	
	if(this.message < this.messages.length && this.ticksExisted == this.message_times[this.message])
	{
	    message_func.accept(this.messages[this.message]);
	    this.message++;
	}
	
	// After the last message is finished, call onMessageEnd
	if(this.message == this.messages.length && !this.calledMessage)
	{
	    this.onMessageEnd.accept("");
	    this.calledMessage = true;
	}
	
	this.ticksExisted++;
    }
}
