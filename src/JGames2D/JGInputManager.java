/***********************************************************************
*Name: JGInputManager
*Description: handle the user events like mouse and keyboard
*Author: Silvano Malfatti
*Date: 01/05/20
************************************************************************/

//Package declaration
package JGames2D;

//Used Packages
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

public class JGInputManager implements KeyListener, MouseListener, MouseMotionListener
{
	//Class attributes
	private final int KEYS_NUMBER = 256;
	private boolean[] keyStates = null;
	private boolean[] prevKeyStates = null;
	private JGVector2D mousePosition = null;
	private boolean mouseState = false;
	private boolean mousePrevState = false;
	
	/*******************************************
   	* Name: JGInputManager
   	* Description: user event handler
   	* Parameters: JFrame
   	* Returns: none
   	******************************************/
	public JGInputManager(JFrame window)
	{
		mousePosition = new JGVector2D();
		
		keyStates = new boolean[KEYS_NUMBER];
		prevKeyStates = new boolean[KEYS_NUMBER];
		
		window.addKeyListener(this);
		window.addMouseMotionListener(this);
		window.addMouseListener(this);
	}
	
	/*******************************************
   	* Name: reset()
   	* Description: reset event handler
   	* Parameters: none
   	* Returns: none
   	******************************************/
	public void reset()
	{
		for (int index=0; index < keyStates.length; index++)
		{
			keyStates[index] = false;
			prevKeyStates[index] = false;
			
		}
		mouseState = false;
		mousePrevState = false;
	}
	
	/*******************************************
   	* Name: keyReleased
   	* Description: returns if key was released
   	* Parameters: none
   	* Returns: JGVector2D
   	******************************************/
	public boolean keyReleased(int keyCode)
	{
		return (prevKeyStates[keyCode]) && !(keyStates[keyCode]) ? true : false;
	}
	
	/***********************************************************
	*Name: mouseReleased
	*Description: returns if button mouse released
	*Params: none
	*Return: boolean
	************************************************************/
	public boolean mouseReleased()
	{
		return (mousePrevState && !mouseState) ? true : false;
	}
	
	/*******************************************
   	* Name: keyWasPressed
   	* Description: returns if key was pressed before
   	* Parameters: int
   	* Returns: boolean
   	******************************************/
	public boolean keyTyped(int keyCode)
	{
		boolean result = prevKeyStates[keyCode] && !keyStates[keyCode];

		prevKeyStates[keyCode] = false;
		
		return result; 
	}
	
	/****************************************************** *****
	*Name: mouseClicked
	*Description: returns if button mouse clicked (up / down)
	*Params: none
	*Return: boolean
	************************************************************/
	public boolean mouseClicked()
	{
		boolean result = (mousePrevState && !mouseState);
		
		mousePrevState = false;
		
		return result;
	}
	
	/*******************************************
   	* Name:keyIsPressed
   	* Description: returns if key is pressed now
   	* Parameters: int
   	* Returns: boolean
   	******************************************/
	public boolean keyPressed(int keyCode)
	{
		return keyStates[keyCode];
	}

	/***********************************************************
	*Name: mouseEntered()
	*Description: method da interface MouseMotionListener
	*Params: MouseEvent
	*Return: Nenhum
	************************************************************/
	public boolean mousePressed()
	{
		return mouseState;
	}
	/*******************************************
   	* Name: getMousePosition
   	* Description: returns the last mouse position
   	* Parameters: none
   	* Returns: JGVector2D
   	******************************************/
	public JGVector2D getMousePosition()
	{
		return mousePosition;
	}
	
	/***********************************************************
	*Name: keyPressed()
	*Description: method of interface KeyListener
	*Params: KeyEvent
	*Return: Nenhum
	************************************************************/ 
	public void keyPressed(KeyEvent e)
	{
		if(e.getKeyCode() < KEYS_NUMBER)
		{
			keyStates[e.getKeyCode()] = true;
		}
	}
	
	/***********************************************************
	*Name: keyRelesead()
	*Description: method of interface KeyListener
	*Params: KeyEvent
	*Return: Nenhum
	************************************************************/
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode() < KEYS_NUMBER)
		{
			prevKeyStates[e.getKeyCode()] = keyStates[e.getKeyCode()];
			keyStates[e.getKeyCode()] = false;
		}
	}
	
	/***********************************************************
	*Name: keyTyped()
	*Description: method of interface KeyListener
	*Params: KeyEvent
	*Return: Nenhum
	************************************************************/
	public void keyTyped(KeyEvent e)
	{
		
	}
	
	/***********************************************************
	*Name: mouseMoved()
	*Description: method of interface MouseMotionListener
	*Params: MouseEvent
	*Return: Nenhum
	************************************************************/
	public void mouseMoved(MouseEvent e)
	{
		mousePrevState = false;
		
		mousePosition.setX(e.getX());
		mousePosition.setY(e.getY());
	}
	
	/***********************************************************
	*Name: mouseDragged()
	*Description: method of interface MouseMotinListener
	*Params: MouseEvent
	*Return: Nenhum
	************************************************************/
	public void mouseDragged(MouseEvent e)
	{
		mousePrevState = false;
		mousePosition.setX(e.getX());
		mousePosition.setY(e.getY());
	}
	
	/***********************************************************
	*Name: mouseExited()
	*Description: method of interface MouseMotionListener
	*Params: MouseEvent
	*Return: Nenhum
	************************************************************/
	public void mouseExited(MouseEvent e)
	{
	
	}
	
	/***********************************************************
	*Name: mouseReleased()
	*Description: mmethod of interface MouseMotionListener
	*Params: MouseEvent
	*Return: Nenhum
	************************************************************/
	public void mouseReleased(MouseEvent e)
	{
		mousePrevState = mouseState;
		mouseState = false;
	}
	
	/***********************************************************
	*Name: mouseClicked()
	*Description: method of interface MouseMotionListener
	*Params: MouseEvent
	*Return: Nenhum
	************************************************************/
	public void mouseClicked(MouseEvent e)
	{
		
	}
	
	/***********************************************************
	*Name: mouseEntered()
	*Description:method of interface MouseMotionListener
	*Params: MouseEvent
	*Return: Nenhum
	************************************************************/
	public void mouseEntered(MouseEvent e)
	{
	
	}
	
	/***********************************************************
	*Name: mouseEntered()
	*Description: method of interface MouseMotionListener
	*Params: MouseEvent
	*Return: Nenhum
	************************************************************/
	public void mousePressed(MouseEvent e)
	{
		mouseState = true;
	}
	
	/***********************************************************
	*Name: free()
	*Description: free resources
	*Params: none
	*Return: none
	************************************************************/
	public void free()
	{
		keyStates = null;
		prevKeyStates = null;
		mousePosition.free();
		mousePosition = null;
	}
}
