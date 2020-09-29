package utils;

import static java.util.Arrays.stream;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import model.Point;

/** Created by Dmitriy Stepanov on 20.11.17. */
public class GridUtils {

  public static int[] fromString(String string) {
    return stream(string.replaceAll("\n", " ").split(" ")).mapToInt(Integer::parseInt).toArray();
  }

  public static Point[] parseMask(String string) {
    return stream(string.split(";")).map(Point::new).toArray(Point[]::new);
  }

  public static Integer applyMax(
      String[] masks, int[] grid, int dimension, BinaryOperator<Integer> f) {
    List<Point[]> masks_parsed =
        stream(masks).map(GridUtils::parseMask).collect(Collectors.toList());
    Integer max = 0;
    for (Point[] mask : masks_parsed) {
      for (int i = 0; i < grid.length; i++) {
        // borders
        int finalI = i;
        int pos = i % dimension;
        try {
          Integer result =
              stream(mask)
                  .map(
                      p -> {
                        int index = finalI + p.x + p.y * dimension;
                        if (pos + p.x >= dimension) {
                          throw new ArrayIndexOutOfBoundsException();
                        }
                        return grid[index];
                      })
                  .reduce(f)
                  .get();
          if (result > max) {
            max = result;
          }
        } catch (ArrayIndexOutOfBoundsException e) {
          continue;
        }
      }
    }
    return max;
  }
}
