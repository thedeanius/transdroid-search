package org.transdroid.search.gui;

import org.transdroid.search.TorrentSite;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SettingsHelper {

	// Used to store if a site is enabled/disabled (with the site name appended to this key)
	static final String PREF_SITE_ENABLED = "pref_key_site_";
	// Used to store a private site's user credentials (with the site name appended to this key)
	static final String PREF_SITE_USER = "pref_key_user_";
	static final String PREF_SITE_PASS = "pref_key_pass_";

	/**
	 * Determines if a torrent site is currently enabled by the user, based on the user settings. Public sites are
	 * simply enabled/disabled with a check box but private sites are only enabled when proper user credentials have
	 * been supplied.
	 * @param site The site for which to determine if it is enabled
	 * @param context The android activity or prodiver context to access shared preferences from
	 * @return True if the site is enabled and should be available in the search and torrent site providers; false
	 *         otherwise
	 */
	public static boolean isSiteEnabled(Context context, TorrentSite site) {
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
		
		// For public sites use the PREF_SITE_ENABLED-based preference only
		if (!site.getAdapter().isPrivateSite())
			return prefs.getBoolean(PREF_SITE_ENABLED + site.name(), true);
		
		// For private sites see if a username and password are specified as well
		if (!prefs.getBoolean(PREF_SITE_ENABLED + site.name(), true))
			return false;
		if (prefs.getString(PREF_SITE_USER + site.name(), null) == null)
			return false;
		if (prefs.getString(PREF_SITE_PASS + site.name(), null) == null)
			return false;
		return true;
		
	}

}