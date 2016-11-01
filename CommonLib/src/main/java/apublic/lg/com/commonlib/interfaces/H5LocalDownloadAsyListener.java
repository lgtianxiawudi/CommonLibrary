package apublic.lg.com.commonlib.interfaces;

/**
 * Created by ligang967 on 16/11/1.
 */
public interface H5LocalDownloadAsyListener extends H5LocalDownloadSynListener {
    public void onStart();
    public void onSuccess(String path);
    public void onFail(Exception e);
}
