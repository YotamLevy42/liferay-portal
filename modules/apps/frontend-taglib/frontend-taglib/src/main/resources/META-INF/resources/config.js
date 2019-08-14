/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

(function() {
	AUI().applyConfig({
		groups: {
			'frontend-taglib': {
				base: MODULE_PATH + '/',
				combine: Liferay.AUI.getCombine(),
				filter: Liferay.AUI.getFilterConfig(),
				modules: {
					'liferay-diff-version-comparator': {
						path:
							'diff_version_comparator/js/diff_version_comparator.js',
						requires: [
							'aui-io-request',
							'autocomplete-base',
							'autocomplete-filters',
							'liferay-portlet-base'
						]
					},
					'liferay-management-bar': {
						path: 'management_bar/js/management_bar.js',
						requires: ['aui-component', 'liferay-portlet-base']
					},
					'liferay-sidebar-panel': {
						path: 'sidebar_panel/js/sidebar_panel.js',
						requires: [
							'aui-base',
							'aui-debounce',
							'aui-parse-content',
							'liferay-portlet-base'
						]
					}
				},
				root: MODULE_PATH + '/'
			}
		}
	});
})();
