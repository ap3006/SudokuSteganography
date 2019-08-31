
public class PixelPair{

  int x;
  int y;

  public PixelPair(int x, int y){
    this.x = x;
    this.y = y;
  }

  public int getX(){
    return this.x;
  }

  public int getY(){
    return this.y;
  }

  public void setX(int value){
    this.x = value;
  }

  public void setY(int value){
    this.y = value;
  }

  public String toString(){
    return "(" + this.x + ", " + this.y + ")";
  }

}
