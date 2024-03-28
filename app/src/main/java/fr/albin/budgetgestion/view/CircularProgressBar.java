package fr.albin.budgetgestion.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import fr.albin.budgetgestion.R;

public class CircularProgressBar extends View {

    private Paint backgroundPaint;
    private Paint progressPaint;
    private Paint textPaint;
    private float progress = 0; // Valeur de progression (entre 0 et 100)
    String progressText;

    public CircularProgressBar(Context context) {
        super(context);
        init();
    }

    public CircularProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        backgroundPaint = new Paint();
        backgroundPaint.setColor(getResources().getColor(R.color.gray_light));
        backgroundPaint.setStyle(Paint.Style.STROKE);
        backgroundPaint.setStrokeWidth(25);

        progressPaint = new Paint();
        progressPaint.setColor(getResources().getColor(R.color.blue));
        progressPaint.setStyle(Paint.Style.STROKE);
        progressPaint.setStrokeWidth(25);

        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(70);
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        float centerX = width / 2;
        float centerY = height / 2;
        float radius = Math.min(centerX, centerY) - 10;

        RectF oval = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);

        // Dessiner l'arrière-plan
        canvas.drawArc(oval, -90, 360, false, backgroundPaint);

        // Dessiner la progression

        //Changer la couleur en fonction de la progression
        changeColor(progress);

        float sweepAngle = progress * 3.6f; // Conversion de pourcentage en degrés (360 degrés pour 100%)
        canvas.drawArc(oval, -90, sweepAngle, false, progressPaint);

        // Dessiner le texte au centre

        float textX = centerX;
        float textY = centerY - ((textPaint.descent() + textPaint.ascent()) / 2);
        canvas.drawText(progressText, textX, textY, textPaint);
    }

    private void changeColor(float progress) {
        if (0<=progress && progress<=20){
            progressPaint.setColor(getResources().getColor(R.color.red));
        }
        if (20<progress && progress<40){
            progressPaint.setColor(getResources().getColor(R.color.orange));
        }
        if (40<=progress && progress<60){
            progressPaint.setColor(getResources().getColor(R.color.yellow));
        }
        if (60<=progress && progress<=80){
            progressPaint.setColor(getResources().getColor(R.color.light_green));
        }
        if (80<progress){
            progressPaint.setColor(getResources().getColor(R.color.green));
        }
    }

    public void setProgress(float progress,String solde) {
        progressText = solde;

        this.progress = progress;
        invalidate(); // Demande de redessiner la vue

    }
}
