package newhome.baselibrary.ThreadAndService;

import android.os.AsyncTask;

/**
 * Created by 20160330 on 2017/4/7 0007.
 */

public class MyDownloadTask  extends AsyncTask<Void, Integer, Boolean> {
    @Override
    protected void onPreExecute() {
//        progressDialog.show(); // 显示进度对话框
    }
    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            while (true) {
//                int downloadPercent = doDownload(); // 这是一个虚构的方法
//                publishProgress(downloadPercent);
//                if (downloadPercent >= 100) {
//                    break;
//                }
            }
        } catch (Exception e) {
            return false;
        }
//        return true;
    }
    /*
       /*
    onPostExecute(Result)
当后台任务执行完毕并通过 return 语句进行返回时，这个方法就很快会被调用。返
回的数据会作为参数传递到此方法中，可以利用返回的数据来进行一些 UI 操作
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
// 在这里更新下载进度
//        progressDialog.setMessage("Downloaded " + values[0] + "%");
    }
    /*
 onPostExecute(Result)
当后台任务执行完毕并通过 return 语句进行返回时，这个方法就很快会被调用。返
回的数据会作为参数传递到此方法中，可以利用返回的数据来进行一些 UI 操作
  */
    @Override
    protected void onPostExecute(Boolean result) {
//        progressDialog.dismiss(); // 关闭进度对话框
// 在这里提示下载结果
        if (result) {
//            Toast.makeText(context, "Download succeeded",
//                    Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(context, " Download failed",
//                    Toast.LENGTH_SHORT).show();
        }
    }
}
