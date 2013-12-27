/*
 * Copyright 2013 Midhun Harikumar
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ae.apps.messagecounter.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ae.apps.messagecounter.R;
import com.ae.apps.messagecounter.fragments.AboutFragment;
import com.ae.apps.messagecounter.fragments.DummySectionFragment;
import com.ae.apps.messagecounter.fragments.MessageChartFragment;
import com.ae.apps.messagecounter.fragments.MessageListFragment;

/**
 * The Pager Adapter that we will use
 * 
 * @author midhun
 * 
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {
	private Context	context;

	public SectionsPagerAdapter(Context context, FragmentManager fm) {
		super(fm);
		this.context = context;
	}

	@Override
	public Fragment getItem(int i) {
		Fragment fragment = null;
		if (i == 0) {
			fragment = new MessageChartFragment();
		} else if (i == 1) {
			fragment = new MessageListFragment();
		} else if (i == 2) {
			fragment = new AboutFragment();
		} else {
			Bundle args = new Bundle();
			fragment = new DummySectionFragment();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, i + 1);
			fragment.setArguments(args);
		}
		return fragment;
	}

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		switch (position) {
		case 0:
			return context.getString(R.string.title_section2).toUpperCase();
		case 1:
			return context.getString(R.string.title_section1).toUpperCase();
		case 2:
			return context.getString(R.string.title_section3).toUpperCase();
		}
		return null;
	}

}