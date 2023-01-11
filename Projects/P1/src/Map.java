import java.util.HashMap;
import java.util.HashSet;
import javax.swing.JComponent;

import com.sun.jdi.Location;

public class Map {

  public enum Type {
    EMPTY,
    PACMAN,
    GHOST,
    WALL,
    COOKIE
  }

  private HashMap<Location, HashSet<Type>> field;
  private boolean gameOver;
  private int dim;

  private HashMap<String, Location> locations;
  private HashMap<String, JComponent> components;
  private HashSet<Type> emptySet;
  private HashSet<Type> wallSet;

  private int cookies = 0;

  public Map(int dim) {
    gameOver = false;
    locations = new HashMap<String, Location>();
    components = new HashMap<String, JComponent>();
    field = new HashMap<Location, HashSet<Type>>();

    emptySet = new HashSet<Type>();
    wallSet = new HashSet<Type>();
    emptySet.add(Type.EMPTY);
    wallSet.add(Type.WALL);
    this.dim = dim;
  }

  public void add(String name, Location loc, JComponent comp, Type type) {
    locations.put(name, loc);
    components.put(name, comp);
    if (!field.containsKey(loc))
      field.put(loc, new HashSet<Type>());
    field.get(loc).add(type);
  }

  public int getCookies() {
    return cookies;
  }

  public boolean isGameOver() {
    return gameOver;
  }

  public boolean move(String name, Location loc, Type type) {
    Location objectLocation = locations.get(name);
    JComponent objectComponent = components.get(name);
    field.get(objectLocation).remove(type);

    if (field.containsKey(loc) == false) {
      field.put(loc, new HashSet<Type>());
    }

    field.get(loc).add(type);
    locations.put(name, loc);

    objectComponent.setLocation(loc.x, loc.y);

    return true;
  }

  /*
   * For the given location argument, returns what is currently at the location
   * (Empty, Pacman, Cookie, Ghost, Wall).
   */
  public HashSet<Type> getLoc(Location loc) {
    // wallSet and emptySet will help you write this method
    if(field.get(loc).size()>0){
      return field.get(loc);
    }else if (field.get(loc).size()<=0){
      return emptySet;
    }else if((loc.x > dim || loc.y > dim ||loc.y < 0 || loc.x < 0 )){
      return wallSet;
    }
    return emptySet;
  }


 /**
  * // When a ghost attacks, ghost.attack() calls Map.attack()
  */
 public boolean attack(String Name) {
    // if pacman is attacked, the game is over
    gameOver = true;
    return gameOver;
  }

  public JComponent eatCookie(String name) {
    // update locations, components, field, and cookies
    // the id for a cookie at (10, 1) is tok_x10_y1
    if(!components.containsKey(name)){
      return null;
    }
    cookies++;
    Location cookieLoc = locations.get(name);
    JComponent cookieEaten = components.get(name);
    components.remove(name);
    locations.replace(name, null);
    field.remove(cookieLoc);


    return cookieEaten;
  }
}
