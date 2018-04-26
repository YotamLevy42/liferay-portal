<%--
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
--%>

<%@ include file="/document_library/init.jsp" %>

<%
long repositoryId = GetterUtil.getLong((String)request.getAttribute("view.jsp-repositoryId"));

long folderId = GetterUtil.getLong((String)request.getAttribute("view.jsp-folderId"));

long fileEntryTypeId = ParamUtil.getLong(request, "fileEntryTypeId", -1);

DLPortletInstanceSettingsHelper dlPortletInstanceSettingsHelper = new DLPortletInstanceSettingsHelper(dlRequestHelper);
%>

<clay:management-toolbar
	clearResultsURL="<%= dlAdminDisplayContext.getClearResultsURL() %>"
	creationMenu="<%= dlAdminDisplayContext.getCreationMenu() %>"
	disabled="<%= DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(repositoryId, folderId, WorkflowConstants.STATUS_ANY, true) <= 0 %>"
	filterItems="<%= dlAdminDisplayContext.getFilterDropdownItems() %>"
	infoPanelId="infoPanelId"
	searchActionURL="<%= String.valueOf(dlAdminDisplayContext.getSearchURL()) %>"
	searchContainerId="entries"
	selectable="<%= dlPortletInstanceSettingsHelper.isShowActions() %>"
	showInfoButton="<%= true %>"
	showSearch="<%= dlPortletInstanceSettingsHelper.isShowSearch() %>"
	sortingOrder="<%= dlAdminDisplayContext.getOrderByType() %>"
	sortingURL="<%= String.valueOf(dlAdminDisplayContext.getSortingURL()) %>"
	totalItems="<%= dlAdminDisplayContext.getTotalItems() %>"
	viewTypes="<%= dlAdminDisplayContext.getViewTypes() %>"
/>

<liferay-frontend:management-bar
	disabled="<%= DLAppServiceUtil.getFoldersAndFileEntriesAndFileShortcutsCount(repositoryId, folderId, WorkflowConstants.STATUS_ANY, true) <= 0 %>"
	includeCheckBox="<%= dlPortletInstanceSettingsHelper.isShowActions() %>"
	searchContainerId="entries"
>
	<liferay-frontend:management-bar-action-buttons>
		<liferay-frontend:management-bar-sidenav-toggler-button
			icon="info-circle"
			label="info"
		/>

		<%
		Group scopeGroup = themeDisplay.getScopeGroup();
		%>

		<c:if test="<%= !user.isDefaultUser() && (!scopeGroup.isStaged() || scopeGroup.isStagingGroup() || !scopeGroup.isStagedPortlet(DLPortletKeys.DOCUMENT_LIBRARY)) %>">

			<%
			String taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: 'download'}); void(0);";
			%>

			<liferay-frontend:management-bar-button
				href="<%= taglibURL %>"
				icon="download"
				label="download"
			/>

			<%
			taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.CHECKIN + "'}); void(0);";
			%>

			<liferay-frontend:management-bar-button
				href="<%= taglibURL %>"
				icon="unlock"
				label="unlock"
			/>

			<%
			taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.CHECKOUT + "'}); void(0);";
			%>

			<liferay-frontend:management-bar-button
				href="<%= taglibURL %>"
				icon="lock"
				label="lock"
			/>

			<%
			taglibURL = "javascript:Liferay.fire('" + renderResponse.getNamespace() + "editEntry', {action: '" + Constants.MOVE + "'}); void(0);";
			%>

			<liferay-frontend:management-bar-button
				href="<%= taglibURL %>"
				icon="change"
				label="move"
			/>
		</c:if>

		<c:if test="<%= !user.isDefaultUser() %>">
			<liferay-frontend:management-bar-button
				href='<%= "javascript:" + renderResponse.getNamespace() + "deleteEntries();" %>'
				icon='<%= dlTrashUtil.isTrashEnabled(scopeGroupId, repositoryId) ? "trash" : "times" %>'
				id="deleteAction"
				label='<%= dlTrashUtil.isTrashEnabled(scopeGroupId, repositoryId) ? "recycle-bin" : "delete" %>'
			/>
		</c:if>
	</liferay-frontend:management-bar-action-buttons>
</liferay-frontend:management-bar>

<aui:script>
	function <portlet:namespace />deleteEntries() {
		if (<%= dlTrashUtil.isTrashEnabled(scopeGroupId, repositoryId) %> || confirm('<%= UnicodeLanguageUtil.get(request, "are-you-sure-you-want-to-delete-the-selected-entries") %>')) {
			Liferay.fire(
				'<%= renderResponse.getNamespace() %>editEntry',
				{
					action: '<%= dlTrashUtil.isTrashEnabled(scopeGroupId, repositoryId) ? Constants.MOVE_TO_TRASH : Constants.DELETE %>'
				}
			);
		}
	}
</aui:script>

<aui:script use="liferay-item-selector-dialog">
	<portlet:renderURL var="viewFileEntryTypeURL">
		<portlet:param name="mvcRenderCommandName" value="/document_library/view" />
		<portlet:param name="browseBy" value="file-entry-type" />
		<portlet:param name="folderId" value="<%= String.valueOf(rootFolderId) %>" />
	</portlet:renderURL>

	window.<portlet:namespace />openDocumentTypesSelector = function() {
		var itemSelectorDialog = new A.LiferayItemSelectorDialog(
			{
				eventName: '<portlet:namespace />selectFileEntryType',
				on: {
					selectedItemChange: function(event) {
						var selectedItem = event.newVal;

						if (selectedItem) {
							var uri = '<%= viewFileEntryTypeURL %>';

							uri = Liferay.Util.addParams('<portlet:namespace />fileEntryTypeId=' + selectedItem, uri);

							location.href = uri;
						}
					}
				},
				'strings.add': '<liferay-ui:message key="done" />',
				title: '<liferay-ui:message key="select-document-type" />',
				url: '<portlet:renderURL windowState="<%= LiferayWindowState.POP_UP.toString() %>"><portlet:param name="mvcPath" value="/document_library/select_file_entry_type.jsp" /><portlet:param name="fileEntryTypeId" value="<%= String.valueOf(fileEntryTypeId) %>" /></portlet:renderURL>'
			}
		);

		itemSelectorDialog.open();
	}
</aui:script>