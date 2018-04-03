package top.omooo.blackfish.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.util.AttributeSet;

import java.util.List;

import top.omooo.blackfish.R;

/**
 * Created by SSC on 2018/4/3.
 */

/**
 * 数字键盘
 */
public class NumberKeyBoardView extends KeyboardView implements KeyboardView.OnKeyboardActionListener {

    private static final int KEYCODE_EMPTY = -10;
    private OnKeyBoardClickListener mClickListener;

    public NumberKeyBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NumberKeyBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public NumberKeyBoardView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);

    }

    private void init(Context context) {
        Keyboard keyboard = new Keyboard(context, R.xml.keyboard_only_number);
        setKeyboard(keyboard);
        setEnabled(true);
        setPreviewEnabled(false);
        setOnKeyboardActionListener(this);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        List<Keyboard.Key> keys = getKeyboard().getKeys();
        for (Keyboard.Key key : keys) {
            if (key.codes[0] == KEYCODE_EMPTY) {
                drawKeyBackground(key, canvas);
            } else if (key.codes[0] == Keyboard.KEYCODE_DELETE) {
                drawKeyBackground(key, canvas);
                drawDeleteKey(key, canvas);
            }
        }
    }

    private void drawKeyBackground(Keyboard.Key key, Canvas canvas) {
        ColorDrawable drawable = new ColorDrawable(Color.parseColor("#EBEBEB"));
        drawable.setBounds(key.x, key.y, key.x + key.width, key.y + key.height);
        drawable.draw(canvas);
    }

    //绘制删除键
    private void drawDeleteKey(Keyboard.Key key, Canvas canvas) {
        Drawable deleteDrawable = getResources().getDrawable(R.drawable.icon_key_board_delete);
        int width = deleteDrawable.getIntrinsicWidth();
        int height = deleteDrawable.getIntrinsicHeight();
        int drawWidth = width;
        int drawHeight = height;
        if (drawWidth > key.width) {
            drawWidth = key.width/2;
            drawHeight = drawWidth * height / width;
        }
        if (drawHeight > key.height) {
            drawHeight = key.height/2;
            drawWidth = drawHeight * width / height;
        }

        //获取删除图标绘制的坐标
        int left = key.x + (key.width - drawWidth) / 2;
        int top = key.y + (key.height - drawHeight) / 2;
        Rect rect = new Rect(left, top, left + drawWidth, top + drawHeight);
        deleteDrawable.setBounds(rect.left, rect.top, rect.right, rect.bottom);
        deleteDrawable.draw(canvas);
    }

    @Override
    public void swipeRight() {

    }

    @Override
    public void swipeLeft() {

    }

    @Override
    public void swipeUp() {

    }

    @Override
    public void swipeDown() {

    }

    @Override
    public void onPress(int primaryCode) {

    }

    @Override
    public void onRelease(int primaryCode) {

    }

    @Override
    public void onKey(int primaryCode, int[] keyCodes) {
        if (primaryCode == Keyboard.KEYCODE_DELETE) {
            if (null != mClickListener) {
                mClickListener.onDeleteKeyEvent();
            }
        } else if (primaryCode != KEYCODE_EMPTY) {
            if (null != mClickListener) {
                mClickListener.onInsertKeyEvent(Character.toString((char) primaryCode));
            }
        }
    }

    @Override
    public void onText(CharSequence text) {

    }

    public void setOnKeyBoardClickListener(OnKeyBoardClickListener onKeyBoardClickListener) {

    }
    public interface OnKeyBoardClickListener {
        void onInsertKeyEvent(String text);

        void onDeleteKeyEvent();
    }
}
