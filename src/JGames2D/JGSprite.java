/***********************************************************************
*Name: JGSprite
*Description: represents a visual object
*Author: Silvano Malfatti
*Date: 01/05/20
************************************************************************/

//Package declaration
package JGames2D;

//Used packages
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ArrayList;

public class JGSprite 
{
	//Class attributes
	private BufferedImage[][] vetQuads = null;
	private AffineTransform transform = null;
	private int currentAnimation;
	private boolean mirror = false;
	private int countLines = 0;
	private int countColunms = 0;
	public JGVector2D speed = null;
	public JGVector2D position = null;
	public JGVector2D direction = null;
	public JGVector2D zoom = null;
	public JGVector2D lastPosition = null;
	public JGVector2D initMove = null;
	public JGVector2D endMove = null;
	public JGImage spriteImage = null;
	public ArrayList<JGAnimation> vetAnim = null;
	public JGEngine gameManager = null;
	public JGTimer moveTimer = null;
	public boolean visible;
	public boolean autoRender;
	public int imageWidth = 0;
	public int imageHeight = 0;
	public int frameWidth = 0;
	public int frameHeight = 0;
	public float fAngle = 0.0f;
	
	/***********************************************************
	*Name: JGSprite
	*Description: constructor
	*Parameters: None
	*Return: None
	************************************************************/
	public JGSprite(JGEngine manager, int lin, int col)
	{
		countLines = lin;
		countColunms = col;
		
		speed = new JGVector2D();
		direction = new JGVector2D();
		position = new JGVector2D();
		zoom = new JGVector2D(1,1);
		lastPosition = new JGVector2D();
		initMove = new JGVector2D();
		endMove = new JGVector2D();
		
		vetAnim = new ArrayList<JGAnimation>();
		currentAnimation=-1;
		visible = true;
		autoRender = false;
		gameManager = manager;
		moveTimer = new JGTimer(0);
		
		transform = new AffineTransform();
	}
	
	/***********************************************************
	*Name: getRectangle
	*Description: returns the rectangle with the area used by Sprite
	*Parameters: None
	*Return: None
	************************************************************/
	public Rectangle getRectangle()
	{
		return new Rectangle((int)(position.getX() - (frameWidth / 2) * zoom.getX()), 
				             (int)(position.getY() - (frameHeight / 2)*zoom.getY()),
				             (int)(frameWidth * zoom.getX()), (int)(frameHeight * zoom.getY()));
	}
	
	/***********************************************************
	*Name: isMoveEnded
	*Description: returns if the move has ended
	*Parameters: none
	*Return: boolean
	************************************************************/
	public boolean isMoveEnded()
	{
		return (moveTimer.getEndTime() <= 0);
	}
	
	/***********************************************************
	*Name: moveTo
	*Description: inits a sprite move by a time interval
	*Parameters: int, JGVector2D
	*Return: none
	************************************************************/
	public void moveTo(int timer, JGVector2D newPosition)
	{
		initMove.setX(position.getX());
		initMove.setY(position.getY());
		
		endMove = newPosition;
		
		if (timer<=0 || (initMove == endMove))
		{
			position.setX(endMove.getX());
			position.setY(endMove.getY());
			moveTimer.restart(0);
		}
		else
		{
			moveTimer.restart(timer);
		}
	}
	
	/***********************************************************
	*Name: updateMove
	*Description: update the move position by the time
	*Parameters: none
	*Return: boolean
	************************************************************/
	void updateMove()
	{
		moveTimer.update();

		if (moveTimer.getEndTime() !=0)
		{
			if (!moveTimer.isTimeEnded())
			{
				position.setX(initMove.getX());
				position.setX(position.getX() + ((initMove.getX() < endMove.getX()) ? -((initMove.getX() - endMove.getX()) * moveTimer.getCurrentTime())/moveTimer.getEndTime() : -(Math.abs(initMove.getX() - endMove.getX()) * moveTimer.getCurrentTime())/moveTimer.getEndTime()));
				position.setY(initMove.getY());
				position.setY(position.getY() + ((initMove.getY() < endMove.getY()) ? -((initMove.getY() - endMove.getY()) * moveTimer.getCurrentTime())/moveTimer.getEndTime(): -(Math.abs(initMove.getY() - endMove.getY()) * moveTimer.getCurrentTime())/moveTimer.getEndTime()));
			}
			else
			{
				position = endMove;
				moveTimer.restart(0);
			}
		}
	}
	
	/***********************************************************
	*Name: setSpriteImage
	*Description: set the sprite reference image
	*Parameters: URL
	*Return: none
	************************************************************/
	public void setSpriteImage(URL imageName)
	{
		spriteImage = JGImageManager.loadImage(imageName);
		
		imageWidth = spriteImage.getImageWidth();
		imageHeight = spriteImage.getImageHeight();
		frameWidth = imageWidth / countColunms;
		frameHeight = imageHeight / countLines;
		
		vetQuads = new BufferedImage[countLines * countColunms][2];
		for (int iIndex=0; iIndex < vetQuads.length; iIndex++)
		{
			vetQuads[iIndex][0] =  new BufferedImage(frameWidth,frameHeight,BufferedImage.TYPE_4BYTE_ABGR);
			vetQuads[iIndex][1] =  new BufferedImage(frameWidth,frameHeight,BufferedImage.TYPE_4BYTE_ABGR);
			
			Graphics2D graphics = vetQuads[iIndex][0].createGraphics();
			drawFrame(iIndex, 0, 0, graphics, false);
			
			graphics = vetQuads[iIndex][1].createGraphics();
			drawFrame(iIndex, 0, 0, graphics, true);
		}
	}
	
	/***********************************************************
	*Name: setMirror
	*Description: sets the mirror mode
	*Parameters:boolean 
	*Return: none
	************************************************************/
	public void setMirror(boolean mirror)
	{
		this.mirror = mirror;
	}
	
	/***********************************************************
	*Name: setCurrentAnimation
	*Description: sets the current animation sprite
	*Parameters: int
	*Return: none
	************************************************************/
	public void setCurrentAnimation(int animationIndex)
	{
		if (animationIndex < 0 || animationIndex > vetAnim.size() - 1)
		{
			return;
		}
		
		if(animationIndex != currentAnimation)
		{
			currentAnimation = animationIndex;
			restartAnimation();
		}
	}
	
	/***********************************************************
	*Name: getCurrentAnimationFrame
	*Description: returns the current frame animation index
	*Parameters: none
	*Return: int
	************************************************************/
	public int getCurrentAnimationFrame()
	{
		return vetAnim.get(currentAnimation).getCurrentFrameIndex();
	}
	
	/***********************************************************
	*Name: GetCurrentAnimationIndex
	*Description: returns the index of current animation
	*Parameters: none
	*Return: int
	************************************************************/
	public int getCurrentAnimationIndex()
	{
		return currentAnimation;
	}
	
	/***********************************************************
	*Name: getCurrentAnimation
	*Description: returns the current animation object
	*Parameters: none
	*Return: JGAnimation
	************************************************************/
	public JGAnimation getCurrentAnimation()
	{
		return vetAnim.get(currentAnimation);
	}
	
	/***********************************************************
	*Name: getMirror
	*Description: returns the mirror mode
	*Parameters: none
	*Return: boolean
	************************************************************/
	public boolean getMirror()
	{
		return mirror;
	}
	
	/***********************************************************
	*Name: restartAnimation
	*Description: restart the current animation
	*Parameters: none
	*Return: none
	************************************************************/
	public void restartAnimation()
	{
		if(currentAnimation >= 0 && currentAnimation < vetAnim.size())
		{
			vetAnim.get(currentAnimation).restart();
		}
	}
	
	/***********************************************************
	*Name: updateSprite
	*Description: updates the sprite states
	*Parameters: none
	*Return: none
	************************************************************/
	public void updateSprite()
	{
		updateMove();
		
		if((currentAnimation >= 0) && (currentAnimation < vetAnim.size()))
		{
			vetAnim.get(currentAnimation).update();
		}
	}
	
	/***********************************************************
	*Name: addAnimation
	*Description: creates and add a new animation to Sprite
	*Parameters: none
	*Return: boolean
	************************************************************/
	public void addAnimation(int iFPS, boolean repeat, int...frames)
	{
		JGAnimation animation= new JGAnimation(frames);

		animation.setLoop(repeat);
		animation.setFPS(iFPS);

		vetAnim.add(animation);
		
		if (vetAnim.size() == 1)
		{
			currentAnimation = 0;
		}
	}
	
	
	/***********************************************************
	*Name: addAnimation
	*Description: creates and add a new animation to Sprite
	*Parameters: none
	*Return: boolean
	************************************************************/
	public void addAnimation(int iFPS, boolean repeat, int initFrame, int endFrame)
	{
		
		//Try the correct sequency
		if (endFrame <= initFrame)
			return;
		
		int[] frames = new int[1 + endFrame - initFrame];
		
		for (int iIndex = 0; iIndex < frames.length; iIndex++)
		{
			frames[iIndex] = initFrame + iIndex;
		}
			
		JGAnimation animation= new JGAnimation(frames);
		animation.setLoop(repeat);
		animation.setFPS(iFPS);

		vetAnim.add(animation);
		
		if (vetAnim.size() == 1)
		{
			currentAnimation = 0;
		}
	}
	
	/***********************************************************
	*Name:render
	*Description: render the sprite into the window
	*Parameters: none
	*Return: none
	************************************************************/
	public void render()
	{
		if (!visible)
		{
			return;
		}
		
		transform.setToIdentity();
		transform.translate(position.getX(), position.getY());
		transform.rotate(fAngle);
		transform.scale(zoom.getX(), zoom.getY());
		transform.translate(-frameWidth / 2, -frameHeight/2);
			
		if (vetAnim.size() == 0)
		{
			if (!mirror)
				gameManager.graphics.drawImage(vetQuads[0][0], transform, null);
			else
				gameManager.graphics.drawImage(vetQuads[0][1], transform, null);
		}
		else
		{
			if (!mirror)
				gameManager.graphics.drawImage(vetQuads[vetAnim.get(currentAnimation).getCurrentFrameIndex()][0],transform, null);
			else
				gameManager.graphics.drawImage(vetQuads[vetAnim.get(currentAnimation).getCurrentFrameIndex()][1],transform, null);
		}
	}
	
	/***********************************************************
	*Name:drawFrame
	*Description: draw a frame selected in the image
	*Parameters: none
	*Return: none
	************************************************************/
	private void drawFrame(int frameIndex, int x, int y, Graphics2D graphics, boolean mirror)
	{
		int sizeX = frameWidth;
		int sizeY = frameHeight;
		
		int fx = (int)((frameIndex % countColunms) * sizeX);
		int fy = (int)((frameIndex / countColunms) * sizeY);
		
		if (!mirror)
		{
			graphics.drawImage(spriteImage.getImage() ,x, y, (x + sizeX), (y + sizeY) ,fx, fy, (fx + sizeX), (fy + sizeY), null);
		}
		else
		{
 			graphics.drawImage(spriteImage.getImage() ,(x + sizeX), y, x, (y + sizeY) ,fx, fy, (fx + sizeX), (fy + sizeY), null);
		}
	}
	
	/***********************************************************
	*Name:collide
	*Description: try if exists collision between sprite areas
	*Parameters: none
	*Return: none
	************************************************************/
	public boolean collide(JGSprite sprite)
	{
		return getRectangle().intersects(sprite.getRectangle());
	}
	
	/***********************************************************
	*Nome: colisaoVertical()
	*Descrição: testa a colisao em Y do sprite com os blocos da camada
	*Parametros: JGLayer
	*Retorno: boolean
	************************************************************/
	public boolean verticalCollide(JGLayer layer)
	{
		int xBlock = 0;
		int yBlock = 0;
		double xPosition = 0.0f;
		double offsetX = layer.offset.getX();
		double offsetY = layer.offset.getY();
		double layerSizeX = layer.layerSize.getX();
		double layerSizeY = layer.layerSize.getY();
		int blockIndex = 0;
		int mult = 0;
		int spriteY1 = 0;
		int spriteY2 = 0;
		int layerY1 =  0;
		int layerY2 =  0;
		int spriteX1 = 0;
		int spriteX2 = 0;
		int layerX1 =  0;
		int layerX2 =  0;
		
		//Retorna se a layer não estiver visível
		if (!layer.visible)
		{
			return false;
		}
		
		//Retorna false, layer nao existe
		if (layer.vetBlocks == null)
		{
			return false;
		}
		
		//Calcula o início da layer em x caso offset seja menor que zero
		if (offsetX > 0)
		{	
			mult = (int)Math.ceil(Math.abs(offsetX) / layer.blockSize.getX());
			xBlock = ((int)layerSizeX - ((mult % (int)layerSizeX))) == (int)layerSizeX ? 0 : ((int)layerSizeX - ((mult % (int)layerSizeX))); 
			offsetX -= mult * layer.blockSize.getX();
		}
		//Guarda o início do offset e o brick inicial em X
		xPosition = offsetX;
		
		//Calcula o início da layer em y caso offset seja menor que zero
		if (offsetY > 0)
		{
			mult = (int)Math.ceil(Math.abs(offsetY) / layer.blockSize.getY());
			yBlock = ((int)layerSizeY - ((mult % (int)layerSizeY))) == (int)layerSizeY ? 0 : ((int)layerSizeY - ((mult % (int)layerSizeY))); 
			offsetY -= mult * layer.blockSize.getY();
		}
		
		//Desenha todos os bricks da layer
		for(int iStartX = xBlock; offsetY < gameManager.windowManager.getHeight(); 
				                  yBlock = (yBlock+1)%(int)layerSizeY, 
				                  offsetY += layer.blockSize.getY(),
				                  xBlock = iStartX,offsetX = (int)xPosition)
		{
			for( ; offsetX < gameManager.windowManager.getWidth(); xBlock = (xBlock+1)%(int)layerSizeX, offsetX += layer.blockSize.getX())
			{	
				blockIndex = xBlock + (yBlock * (int)layerSizeX);
				if (layer.vetBlocks[blockIndex] != null)
				{
						spriteX1 = (int)(position.getX() - (frameWidth / 2)*zoom.getX());
						spriteX2 = spriteX1 + (int)(frameWidth * zoom.getY());
						layerX1 = (int)offsetX;
						layerX2 = (int)(offsetX + layer.getBlockSize().getX());
						
						spriteY1 = (int)(position.getY() - (frameHeight / 2)*zoom.getY());
						spriteY2 = spriteY1 + (int)(frameHeight * zoom.getY());
						layerY1 = (int)offsetY;
						layerY2 = (int)(offsetY + layer.getBlockSize().getY());
						
						if (spriteY1 < layerY2 && layerY1 < spriteY2 &&
								spriteX1 < layerX2 && layerX1 < spriteX2)
						{

							if ((spriteY1 + spriteY2) / 2 > (layerY1 + layerY2)/2)
							{
								position.setY(layerY2 + (frameHeight / 2)*zoom.getY());
							}
							else
							{
								position.setY(layerY1 - (frameHeight / 2)*zoom.getY());
							}
							return true;
						}
				}
			}
		}
		return false;
	}
	
	/***********************************************************
	*Nome: colisaoVertical()
	*Descrição: testa a colisao em Y do sprite com os blocos da camada
	*Parametros: JGLayer
	*Retorno: boolean
	************************************************************/
	public boolean horizontalCollide(JGLayer layer)
	{
		int xBlock = 0;
		int yBlock = 0;
		double xPosition = 0.0f;
		double offsetX = layer.offset.getX();
		double offsetY = layer.offset.getY();
		double layerSizeX = layer.layerSize.getX();
		double layerSizeY = layer.layerSize.getY();
		int blockIndex = 0;
		int mult = 0;
		int spriteY1 = 0;
		int spriteY2 = 0;
		int layerY1 =  0;
		int layerY2 =  0;
		int spriteX1 = 0;
		int spriteX2 = 0;
		int layerX1 =  0;
		int layerX2 =  0;
		
		//Retorna se a layer não estiver visível
		if (!layer.visible)
		{
			return false;
		}
		
		//Retorna false, layer nao existe
		if (layer.vetBlocks == null)
		{
			return false;
		}
		
		//Calcula o início da layer em x caso offset seja menor que zero
		if (offsetX > 0)
		{	
			mult = (int)Math.ceil(Math.abs(offsetX) / layer.blockSize.getX());
			xBlock = ((int)layerSizeX - ((mult % (int)layerSizeX))) == (int)layerSizeX ? 0 : ((int)layerSizeX - ((mult % (int)layerSizeX))); 
			offsetX -= mult * layer.blockSize.getX();
		}
		//Guarda o início do offset e o brick inicial em X
		xPosition = offsetX;
		
		//Calcula o início da layer em y caso offset seja menor que zero
		if (offsetY > 0)
		{
			mult = (int)Math.ceil(Math.abs(offsetY) / layer.blockSize.getY());
			yBlock = ((int)layerSizeY - ((mult % (int)layerSizeY))) == (int)layerSizeY ? 0 : ((int)layerSizeY - ((mult % (int)layerSizeY))); 
			offsetY -= mult * layer.blockSize.getY();
		}
		
		//Desenha todos os bricks da layer
		for(int iStartX = xBlock; offsetY < gameManager.windowManager.getHeight(); 
				                  yBlock = (yBlock+1)%(int)layerSizeY, 
				                  offsetY += layer.blockSize.getY(),
				                  xBlock = iStartX,offsetX = (int)xPosition)
		{
			for( ; offsetX < gameManager.windowManager.getWidth(); xBlock = (xBlock+1)%(int)layerSizeX, offsetX += layer.blockSize.getX())
			{	
				blockIndex = xBlock + (yBlock * (int)layerSizeX);
				if (layer.vetBlocks[blockIndex] != null)
				{
						spriteX1 = (int)(position.getX() - (frameWidth / 2)*zoom.getX());
						spriteX2 = spriteX1 + (int)(frameWidth * zoom.getX());
						layerX1 = (int)offsetX;
						layerX2 = (int)(offsetX + layer.getBlockSize().getX());
						
						spriteY1 = (int)(position.getY() - (frameHeight / 2)*zoom.getY());
						spriteY2 = spriteY1 + (int)(frameHeight * zoom.getY());
						layerY1 = (int)offsetY;
						layerY2 = (int)(offsetY + layer.getBlockSize().getY());
						
						if (spriteY1 < layerY2 && layerY1 < spriteY2 &&
							spriteX1 < layerX2 && layerX1 < spriteX2)
						{
							if ((spriteX1 + spriteX2) / 2 > (layerX1 + layerX2)/2)
							{
								position.setX(layerX2 + (frameWidth / 2)*zoom.getX());
							}
							else
							{
								position.setX(layerX1 - (frameWidth / 2)*zoom.getX());
							}
							return true;
						}
				}
			}
		}
		return false;
	}
	
	/***********************************************************
	*Name:free
	*Description: free sprite resources
	*Parameters: none
	*Return: none
	************************************************************/
	public void free()
	{
		speed = null;
		position = null;
		direction = null;
		zoom = null;
		lastPosition = null;
		initMove = null;
		endMove = null;
		gameManager = null;
		transform = null;
		
		//JGImageManager.free(spriteImage);
		spriteImage = null;
		
		for (JGAnimation anim : vetAnim)
		{
			anim.free();
		}
		vetAnim.clear();
		vetAnim = null;
		
		for(int index = 0; index < vetQuads.length; index++)
		{
			vetQuads[index][0].flush();
			vetQuads[index][1].flush();
			vetQuads[index][0] = null;
			vetQuads[index][1] = null;
			vetQuads[index] = null;
		}
		vetQuads = null;
		
		moveTimer.free();
		moveTimer = null;
	}
}
