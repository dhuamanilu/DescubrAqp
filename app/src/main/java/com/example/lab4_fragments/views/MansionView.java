package com.example.lab4_fragments.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.lab4_fragments.Door;
import com.example.lab4_fragments.Room;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MansionView extends View {

    private Paint paintRoomOutline;
    private Paint paintDoor;
    private Paint paintText;

    private List<Room> rooms = new ArrayList<>();
    private List<Door> doors = new ArrayList<>();

    public MansionView(Context context) {
        super(context);
        init();
        loadCoordinates(context);
    }

    public MansionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        loadCoordinates(context);
    }

    public MansionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
        loadCoordinates(context);
    }

    private void init() {
        paintRoomOutline = new Paint();
        paintRoomOutline.setColor(Color.DKGRAY);
        paintRoomOutline.setStyle(Paint.Style.STROKE);
        paintRoomOutline.setStrokeWidth(5);

        paintDoor = new Paint();
        paintDoor.setColor(Color.YELLOW);
        paintDoor.setStrokeWidth(10);

        paintText = new Paint();
        paintText.setColor(Color.BLACK);
        paintText.setTextSize(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Dibujar los contornos de los cuartos
        for (Room room : rooms) {
            canvas.drawRect(room.getX1(), room.getY1(), room.getX2(), room.getY2(), paintRoomOutline);
            // Posicionar el nombre del cuarto en el centro
            canvas.drawText(room.getName(), (room.getX1() + room.getX2()) / 2 - 40, (room.getY1() + room.getY2()) / 2, paintText);
        }

        // Dibujar las puertas como líneas amarillas
        for (Door door : doors) {
            canvas.drawLine(door.getX1(), door.getY1(), door.getX2(), door.getY2(), paintDoor);
        }
    }

    private void loadCoordinates(Context context) {
        try {
            InputStream inputStream = context.getAssets().open("coordenadas.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts[0].equals("Cuarto")) {
                    String name = parts[1];
                    float x1 = Float.parseFloat(parts[2]);
                    float y1 = Float.parseFloat(parts[3]);
                    float x2 = Float.parseFloat(parts[4]);
                    float y2 = Float.parseFloat(parts[5]);
                    rooms.add(new Room(name, x1, y1, x2, y2));
                } else if (parts[0].equals("Puerta")) {
                    float x1 = Float.parseFloat(parts[1]);
                    float y1 = Float.parseFloat(parts[2]);
                    float x2 = Float.parseFloat(parts[3]);
                    float y2 = Float.parseFloat(parts[4]);
                    doors.add(new Door(x1, y1, x2, y2));
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

