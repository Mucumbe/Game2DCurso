/***********************************************************************
*Name: JGFont
*Description: represents a text font graphics
*Author: Silvano Malfatti
*Date: 01/05/20
************************************************************************/

//Package declaration
package JGames2D;

//Used Packages
import java.awt.Color;
import java.awt.Font;

public class JGFont 
{
	//Class Atributes
	private Color color = null;
	private int size = 12;
	public JGVector2D position = null;
	private Font font = null;
	private JGEngine gameManager = null;
	private String text = "Font 2D";
	private JGVector2D fontSize = null;
	public boolean visible = true;
	public boolean autoRender = true;
	public static Font staticFont = null;
	
	
	
	/***********************************************************
	*Name:JGFont()
	*Description: Constructor
	*Parameters: Color, int, boolean, boolean
	*Return: none
	***********************************************************/
	public JGFont(String type, Color color, int size, boolean italic, boolean bold, JGEngine gameManager)
	{
		this.gameManager = gameManager;
		this.color = color;
		position = new JGVector2D();
		
		if (bold && italic)	
		{
			font = new Font(type,Font.BOLD | Font.ITALIC, size);
		}
		else if (bold)
		{
			font = new Font(type,Font.BOLD | Font.BOLD, size);
		}
		else
		{
			font = new Font(type,Font.BOLD | Font.ITALIC, size);
		}
		
		fontSize = new JGVector2D( gameManager.graphics.getFontMetrics(font).stringWidth(text),  gameManager.graphics.getFontMetrics(font).getHeight() );
	}
	
	/***********************************************************
	*Name:setText()
	*Description: change the font text
	*Parameters: String
	*Return: none
	************************************************************/
	public void setText(String text)
	{
		this.text = text;
		fontSize.setXY( gameManager.graphics.getFontMetrics(font).stringWidth(text),  gameManager.graphics.getFontMetrics(font).getHeight());
	}
	
	/***********************************************************
	*Name:setColor()
	*Description: set the font color
	*Parameters: Color
	*Return: none
	************************************************************/
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	/***********************************************************
	*Name:render()
	*Description: draw the font
	*Parameters:none
	*Return: none
	************************************************************/
	public void render()
	{
		gameManager.graphics.setFont(font);
		gameManager.graphics.setColor(color);
		gameManager.graphics.drawString(text, (int)(position.getX() - fontSize.getX() / 2), (int)(position.getY() - fontSize.getY() / 2));
	}
	
	/***********************************************************
	*Name:free()
	*Description: free font resources
	*Parameters:none
	*Return: none
	************************************************************/
	public void free()
	{
		fontSize = null;
		position = null;
		font = null;
		gameManager = null;
		color = null;
	}
}
