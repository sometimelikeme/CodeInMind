package com.apkclass.tools;

import java.io.BufferedInputStream; 
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.KeyEvent;
import android.widget.Toast;
public class Action { 
	private CustomProgressDialog progressDialog = null;

	static Context ctx;
	public Map<String, String> userMap;
	public Action(Activity activity) {
		ctx = activity;
	}

	/**
	 * <P>
	 * cookie
	 * </P>
	 */
	public String getCookie(Context ctxs) {
		// return Thinksns.getInstance(ctxs).getCookie();
		return null;
	}
	/**
	 * <P>
	 * 版本升级，获取版本号�?
	 * </P>
	 */
	public static String getVersion(Activity act){
		String PastVersion="";
		PackageManager pm = act.getPackageManager();
		try {
			PastVersion = pm.getPackageInfo(act.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {		 
			e.printStackTrace();
		}
		return PastVersion;
	}

	/**
	 * <P>
	 * cookie
	 * </P>
	 */
	public String getToken(Context ctxs) {
		// return Thinksns.getInstance(ctxs).getToken();
		return null;
	}

	OnKeyListener l = new OnKeyListener() {
		@Override
		public boolean onKey(DialogInterface arg0, int arg1, KeyEvent arg2) {
			if (arg2.getKeyCode() == 4) {
				return true;
			}
			return false;
		}
	};

	// 弹出是否�?��应用对话�?
	public void exit(final Activity mCtx) {
		AlertDialog.Builder builder = new Builder(mCtx);
		builder.setTitle("提示")
				.setMessage("确定退出?")
				.setPositiveButton("确定",
						new android.content.DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								mCtx.finish();
							}
						}).setNegativeButton("取消", null).show();

	}

	/**
	 * 查询手机内所有应�?
	 * 
	 * @param context
	 * @return
	 */
	public static List<PackageInfo> getAllApps(Context context) {
		/**
		 * 
		 //set Icon
		 * shareItem.setIcon(pManager.getApplicationIcon(pinfo.applicationInfo
		 * )); //set Application Name
		 * shareItem.setLabel(pManager.getApplicationLabel
		 * (pinfo.applicationInfo).toString()); //set Package Name
		 * shareItem.setPackageName(pinfo.applicationInfo.packageName);
		 */
		PackageManager packageManager = context.getPackageManager();
		// final List<ResolveInfo> apps =
		// packageManager.queryIntentActivities(mainIntent, 0);
		List<PackageInfo> packageInfoList = packageManager
				.getInstalledPackages(0);
		return packageInfoList;
	}

	/**
	 * 获取系统�?��的应用程�?
	 * 
	 * @param context
	 * @return
	 */
	public static List<ResolveInfo> getAllMainApps(Context context) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
		mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		// get all apps
		List<ResolveInfo> apps = packageManager.queryIntentActivities(
				mainIntent, 0);
		return apps;
	}

	/**
	 * 查询手机内非系统应用
	 * 
	 * @param context
	 * @return
	 */
	public static List<PackageInfo> getAlLNotSysApps(Context context) {
		/**
		 * 
		 // PackageInfo p = apps.get(i); //// Log.e("",
		 * String.valueOf(pManager.getApplicationIcon(p.applicationInfo))); //
		 * // Intent intent=new Intent(); //// Log.e("lable",
		 * pManager.getApplicationLabel(p.applicationInfo).toString()); // try {
		 * // if
		 * (manager.getApplicationLabel(p.applicationInfo).toString().contains
		 * ("京东商城")) { // Log.e("lable",
		 * String.valueOf(manager.getApplicationLabel
		 * (p.applicationInfo).toString())); // Log.e("pacagename",
		 * String.valueOf(p.applicationInfo.packageName.toString())); // intent
		 * =manager.getLaunchIntentForPackage(manager.getApplicationLabel(p.
		 * applicationInfo).toString()); //
		 * intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //
		 * mCtx.startActivity(intent); // } // Toast.makeText(mCtx,
		 * "Package not found1!", Toast.LENGTH_SHORT).show(); // } catch
		 * (ActivityNotFoundException noFound) { // Toast.makeText(mCtx,
		 * "Package not found2!", Toast.LENGTH_SHORT).show(); // }
		 */

		List<PackageInfo> apps = new ArrayList<PackageInfo>();
		PackageManager pManager = context.getPackageManager();
		// 获取手机内所有应�?
		List<PackageInfo> paklist = pManager.getInstalledPackages(0);
		for (int i = 0; i < paklist.size(); i++) {
			PackageInfo pak = (PackageInfo) paklist.get(i);
			// 判断是否为非系统预装的应用程�?
			if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) <= 0) {
				// customs applications
				apps.add(pak);
			}
		}
		return apps;
	}

	/**
	 * 查询手机内系统应�?
	 * 
	 * @param context
	 * @return
	 */
	public static List<PackageInfo> getAllSysApps(Context context) {
		List<PackageInfo> apps = new ArrayList<PackageInfo>();
		PackageManager pManager = context.getPackageManager();
		// 获取手机内所有应�?
		List<PackageInfo> paklist = pManager.getInstalledPackages(0);
		for (int i = 0; i < paklist.size(); i++) {
			PackageInfo pak = (PackageInfo) paklist.get(i);
			// 判断是否为非系统预装的应用程�?
			if ((pak.applicationInfo.flags & pak.applicationInfo.FLAG_SYSTEM) > 0) {
				// customs applications
				apps.add(pak);
			}
		}
		return apps;
	} 


	/**
	 * <P>
	 * 进度提示调用开始
	 * </P>
	 */
	public void startProgressDialog(Context context) {
		if (progressDialog == null) {
			progressDialog = CustomProgressDialog.createDialog(context);
			  
		}

		if (context != null) {
			progressDialog.show();
		}

	}

	/**
	 * <P>
	 * 进度提示调用取消
	 * </P>
	 */
	public void stopProgressDialog() {
		if (progressDialog != null) {
			progressDialog.dismiss();
			progressDialog = null;
		}
	}

	public void showLongToast(String str) {
		Toast.makeText(ctx, str, Toast.LENGTH_SHORT).show();
	}

	public void showShortToast(String str) {
		Toast.makeText(ctx, str, Toast.LENGTH_SHORT).show();
	}

	public Bitmap getBitmapFromUrl(String imgUrl) {
		URL url;
		Bitmap bitmap = null;
		try {
			url = new URL(imgUrl);
			InputStream is = url.openConnection().getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			bitmap = BitmapFactory.decodeStream(bis);
			bis.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bitmap;
	}
	public static boolean isNetworkAvailable(Context ctx) {
		Context context = ctx;
		ConnectivityManager connectivity = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connectivity == null) {
			// Error
		} else {
			NetworkInfo[] info = connectivity.getAllNetworkInfo();
			if (info != null) {
				for (int i = 0; i < info.length; i++) {
					if (info[i].getState() == NetworkInfo.State.CONNECTED) {
						return true;
					}
				}
			}
		}
		return false;
	}
	public static JSONArray getJsonArray(String content, String tag)
			throws JSONException {
		JSONArray jsArray = null;
		if (content != null) {
			JSONObject jsObj = new JSONObject(content);
			if (jsObj != null && jsObj.has(tag)) {
				String str_data = jsObj.getString(tag);
				if (str_data != null) {
					jsArray = new JSONArray(str_data);
				}
			}
		}
		return jsArray;
	}
//	/**
//	 * 当前用户关注的列表
//	 * 
//	 * @param getFollowing
//	 * @return
//	 */
//	public ArrayList<FollowingUserObj> getFollowingssss(String uid,
//			String uname, int page) {
//		ArrayList<FollowingUserObj> arraylist = null;
//		String str_users = null;
//		FollowingUserObj userObj = null;
//		try {
//			str_users = Weibo.getFollowings(ctx, uid, uname, page);
//			if (str_users != null && str_users.length() != 0) {
//				JSONArray jsonArray = new JSONArray(str_users);
//				arraylist = new ArrayList<FollowingUserObj>();
//				int iSize = jsonArray.length();
//				for (int i = 0; i < iSize; i++) {
//					JSONObject jsonObj = jsonArray.getJSONObject(i);
//					String str_user = jsonObj.getString("user");
//					if (str_user != null && str_user.length() != 0) {
//						JSONObject jsObj = new JSONObject(str_user);
//						if (jsObj != null) {
//							userObj = new FollowingUserObj();
//							userObj.setIsInBlackList(jsObj
//									.getString("isInBlackList"));
//							userObj.setUid(jsObj.getString("uid"));
//							userObj.setFace(jsObj.getString("face"));
//							userObj.setSex(jsObj.getString("sex"));
//							// userObj.setIs_verified(jsonObj.getString("is_verified"));
//							userObj.setLocation(jsObj.getString("location"));
//							userObj.setUname(jsObj.getString("uname"));
//							userObj.setSpace(jsObj.getString("space"));
//							userObj.setCity(jsObj.getString("city"));
//							userObj.setWeibo_count(jsObj
//									.getString("weibo_count"));
//							userObj.setProvince(jsObj.getString("province"));
//							userObj.setFavorite_count(jsObj
//									.getString("favorite_count"));
//							userObj.setIs_followed(jsObj
//									.getString("is_followed"));
//							userObj.setFollowed_count(jsObj
//									.getString("followed_count"));
//							userObj.setFollowers_count(jsObj
//									.getString("followers_count"));
//							arraylist.add(userObj);
//						}
//					}
//				}
//			}
//			return arraylist;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//	/**
//	 * 当前用户粉丝的列表
//	 * 
//	 * @param getFollowing
//	 * 
//	 */
//	@SuppressLint("ParserError")
//	public ArrayList<FollowingUserObj> getFollowerssss(String uid,
//			String uname, int pages) {
//		ArrayList<FollowingUserObj> arraylist = null;
//		String str_users = null;
//		FollowingUserObj userObj = null;
//		try {
//			str_users = Weibo.getFollowers(ctx, uid, uname, pages);
//			if (str_users != null && str_users.length() != 0) {
//				JSONArray jsonArray = new JSONArray(str_users);
//				arraylist = new ArrayList<FollowingUserObj>();
//				int iSize = jsonArray.length();
//				for (int i = 0; i < iSize; i++) {
//					JSONObject jsonObj = jsonArray.getJSONObject(i);
//					String str_user = jsonObj.getString("user");
//					if (str_user != null && str_user.length() != 0) {
//						JSONObject jsObj = new JSONObject(str_user);
//						if (jsObj != null) {
//							userObj = new FollowingUserObj();
//							if (jsObj.has("isInBlackList")) {
//								userObj.setIsInBlackList(jsObj
//										.getString("isInBlackList"));
//							} else {
//								userObj.setIsInBlackList("self");
//							}
//							userObj.setUid(jsObj.getString("uid"));
//							userObj.setFace(jsObj.getString("face"));
//							userObj.setSex(jsObj.getString("sex"));
//							// userObj.setIs_verified(jsonObj.getString("is_verified"));
//							userObj.setLocation(jsObj.getString("location"));
//							userObj.setUname(jsObj.getString("uname"));
//							userObj.setSpace(jsObj.getString("space"));
//							userObj.setCity(jsObj.getString("city"));
//							userObj.setWeibo_count(jsObj
//									.getString("weibo_count"));
//							userObj.setProvince(jsObj.getString("province"));
//							userObj.setFavorite_count(jsObj
//									.getString("favorite_count"));
//							userObj.setIs_followed(jsObj
//									.getString("is_followed"));
//							userObj.setFollowed_count(jsObj
//									.getString("followed_count"));
//							userObj.setFollowers_count(jsObj
//									.getString("followers_count"));
//							arraylist.add(userObj);
//						}
//					}
//
//				}
//
//			}
//
//			return arraylist;
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//
//	}
}