package model;

/** Created by Dmitriy Stepanov on 9/28/20. */
public class Point {

  public Integer x;
  public Integer y;

  public Point(String str) {
    String[] pair = str.split(",");
    this.x = Integer.parseInt(pair[0]);
    this.y = Integer.parseInt(pair[1]);
  }
}
