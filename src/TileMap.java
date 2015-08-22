import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

/**
 * Created by seyfer on 22.08.15.
 */
public class TileMap
{
    private int x;
    private int y;

    private int tileSize;
    private int[][] map;
    private int mapWidth;
    private int mapHeight;

    public TileMap(String s, int tileSize) {

        this.tileSize = tileSize;

        try {

            BufferedReader br = new BufferedReader(new FileReader(s));

//            URI fileResource = getClass().getResource(s).toURI();
//            BufferedReader br = new BufferedReader(new FileReader(fileResource.getPath()));

//            InputStream in = getClass().getResourceAsStream(s);
//            BufferedReader br = new BufferedReader(new InputStreamReader(in));

            mapWidth = Integer.parseInt(br.readLine());
            mapHeight = Integer.parseInt(br.readLine());

            map = new int[mapHeight][mapWidth];

            String delimiters = " ";
            for (int row = 0; row < mapHeight; row++) {
                String line = br.readLine();
                String tokens[] = line.split(delimiters);
                for (int col = 0; col < mapWidth; col++) {
                    map[row][col] = Integer.parseInt(tokens[col]);
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public int getColTile(int x) {
        return x / tileSize;
    }

    public int getRowTile(int y) {
        return y / tileSize;
    }

    public int getTile(int row, int col) {
        return map[row][col];
    }

    public int getTileSize() {
        return tileSize;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void update() {

    }

    public void draw(Graphics2D g) {
        for (int row = 0; row < mapHeight; row++) {
            for (int col = 0; col < mapWidth; col++) {
                int rc = map[row][col];

                if (rc == 0) {
                    g.setColor(Color.BLACK);
                }

                if (rc == 1) {
                    g.setColor(Color.WHITE);
                }

                g.fillRect(x + col * tileSize, y + row * tileSize, tileSize, tileSize);
            }
        }
    }
}
