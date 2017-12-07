package SimpleControls.MBlueToothTool.Print;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;

import com.lvrenyang.io.Canvas;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import SimpleControls.MBlueToothTool.CanvsModel;

public class PrintUitil {
    private static int nPrintWidth = 384;
    private static int nPrintHeight = 700;


    public static Canvas PrintStringTest(Context ctx, Canvas canvas, CanvsModel
            canvsModel) throws UnsupportedEncodingException {
        boolean bPrintResult = false;
        canvas.CanvasBegin(nPrintWidth, nPrintHeight);
        canvas.SetPrintDirection(0);
        Typeface tfTitle = Typeface.createFromAsset(ctx.getAssets(), "fonts"
                + File.separator + "wenquanyi.ttf");
        Typeface tfContent = Typeface.createFromAsset(ctx.getAssets(), "fonts"
                + File.separator + "simsun.ttc");
        Typeface tfNumber = Typeface.createFromAsset(ctx.getAssets(), "fonts"
                + File.separator + "xichang.otf");
        Bitmap logo = getImageFromAssetsFile(ctx, "print_tongchengpei_1.png");

//        canvas.DrawBox(1, 1, nPrintWidth - 2, nPrintHeight - 2);
        canvas.DrawBitmap(logo, 10, 10, 0);
        canvas.DrawText("e百米同城配", nPrintWidth / 4, 10, 0, tfTitle, 30, 0x00);
        canvas.DrawText("呵护信任，创造价值！", nPrintWidth / 4, 45, 0, tfContent, 20, 0x00);
        canvas.DrawBarcode(canvsModel.getOrderPartId() + "", -3, 80, 0, 3, 65, Canvas.BARCODE_TYPE_CODE128);
        canvas.DrawText(canvsModel.getOrderPartId() + "", nPrintWidth / 2, 145, 0, tfContent, 22, 0x00);

        canvas.DrawLine(0, 170, nPrintWidth, 170);
        canvas.DrawLine(0, 171, nPrintWidth, 171);
        canvas.DrawLine(0, 172, nPrintWidth, 172);
        canvas.DrawText("站点编号:", 5, 175, 0, tfContent, 25, 0x00);
        canvas.DrawText(canvsModel.getStationNo(), 20, 200, 0, tfContent, 40, 0x00);

        canvas.DrawLine(nPrintWidth / 3, 170, nPrintWidth / 3, 245);
        canvas.DrawLine(nPrintWidth / 3, 170, nPrintWidth / 3, 245);
        canvas.DrawLine(nPrintWidth / 3, 170, nPrintWidth / 3, 245);

        canvas.DrawText("箱/包号:", (nPrintWidth * 2 / 5 + 5), 175, 0, tfContent, 25, 0x00);
        canvas.DrawText(canvsModel.getPackageNo(), (nPrintWidth * 2 / 5 + 5), 200, 0, tfContent, 40, 0x00);

        canvas.DrawLine(0, 244, nPrintWidth, 244);
        canvas.DrawLine(0, 245, nPrintWidth, 245);
        canvas.DrawLine(0, 246, nPrintWidth, 246);

        canvas.DrawText("上货点:" + canvsModel.getUpGoodsStation().replace("(站点)", ""), 5, 250, 0, tfContent, 30, 0x00);

        canvas.DrawLine(0, 284, nPrintWidth, 284);
        canvas.DrawLine(0, 285, nPrintWidth, 285);
        canvas.DrawLine(0, 286, nPrintWidth, 286);

        canvas.DrawText("下货点:" + canvsModel.getDownGoodsStation().replace("(站点)", ""), 5, 290, 0, tfContent, 30, 0x00);

        canvas.DrawLine(0, 324, nPrintWidth, 324);
        canvas.DrawLine(0, 325, nPrintWidth, 325);
        canvas.DrawLine(0, 326, nPrintWidth, 326);

        canvas.DrawText("名称", 30, 330, 0, tfContent, 30, 0x00);
        canvas.DrawText("重量", (nPrintWidth / 3 + 30), 330, 0, tfContent, 30, 0x00);
        canvas.DrawText("件数", (nPrintWidth * 2 / 3 + 30), 330, 0, tfContent, 30, 0x00);


        canvas.DrawLine(0, 364, nPrintWidth, 364);
        canvas.DrawLine(0, 365, nPrintWidth, 365);
        canvas.DrawLine(0, 366, nPrintWidth, 366);

        canvas.DrawText(canvsModel.getGoodsName(), 20, 370, 0, tfContent, 30, 0x00);
        canvas.DrawText(canvsModel.getWeight() + "kg", (nPrintWidth / 3 + 30), 370, 0, tfContent, 30, 0x00);
        canvas.DrawText(canvsModel.getNumIndex() + "/" + canvsModel.getTotalNumber(), (nPrintWidth * 2 / 3 + 30), 370, 0, tfContent, 30, 0x00);

        canvas.DrawLine(0, 404, nPrintWidth, 404);
        canvas.DrawLine(0, 405, nPrintWidth, 405);
        canvas.DrawLine(0, 406, nPrintWidth, 406);

        canvas.DrawLine(nPrintWidth / 3, 324, nPrintWidth / 3, 404);
        canvas.DrawLine(nPrintWidth / 3, 325, nPrintWidth / 3, 405);
        canvas.DrawLine(nPrintWidth / 3, 326, nPrintWidth / 3, 406);
        canvas.DrawLine(nPrintWidth * 2 / 3, 324, nPrintWidth * 2 / 3, 404);
        canvas.DrawLine(nPrintWidth * 2 / 3, 325, nPrintWidth * 2 / 3, 405);
        canvas.DrawLine(nPrintWidth * 2 / 3, 326, nPrintWidth * 2 / 3, 406);

        canvas.DrawText("收", 5, 410, 0, tfContent, 30, 0x00);
        canvas.DrawText("件", 5, 445, 0, tfContent, 30, 0x00);
        canvas.DrawText("信", 5, 480, 0, tfContent, 30, 0x00);
        canvas.DrawText("息", 5, 515, 0, tfContent, 30, 0x00);

        canvas.DrawLine(50, 404, 50, 554);
        canvas.DrawLine(50, 405, 50, 555);
        canvas.DrawLine(50, 406, 50, 556);

        canvas.DrawText(canvsModel.getReceiveName(), 50, 410, 0, tfContent, 30, 0x00);
        canvas.DrawText(canvsModel.getReceivePhone(), -3, 410, 0, tfContent, 30, 0x00);
        canvas.DrawText(canvsModel.getReceiveAddress(), 50, 445, 0, tfContent, 30, 0x00);
        if (canvsModel.getReceiveAddress().length() > 12) {
            canvas.DrawText(canvsModel.getReceiveAddress().substring(12, canvsModel.getReceiveAddress().length()),
                    50, 480, 0, tfContent, 30, 0x00);
        }
        if (canvsModel.getReceiveAddress().length() > 24) {
            canvas.DrawText(canvsModel.getReceiveAddress().substring(24, canvsModel.getReceiveAddress().length()),
                    50, 515, 0, tfContent, 30, 0x00);
        }


        canvas.DrawLine(0, 554, nPrintWidth, 554);
        canvas.DrawLine(0, 555, nPrintWidth, 555);
        canvas.DrawLine(0, 556, nPrintWidth, 556);

        canvas.DrawText("签收人:", 5, 560, 0, tfContent, 30, 0x00);

        canvas.DrawLine(0, nPrintHeight - 2, nPrintWidth, nPrintHeight - 2);
        canvas.DrawLine(0, nPrintHeight - 1, nPrintWidth, nPrintHeight - 1);
        canvas.DrawLine(0, nPrintHeight, nPrintWidth, nPrintHeight);

        canvas.CanvasEnd();
        canvas.CanvasPrint(1, 1);
        return canvas;
//        bPrintResult = canvas.GetIO().IsOpened();
//        return bPrintResult;
    }

    /**
     * 从Assets中读取图片
     */
    public static Bitmap getImageFromAssetsFile(Context ctx, String fileName) {
        Bitmap image = null;
        AssetManager am = ctx.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return image;
    }
}
