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

package com.liferay.image.service.internal.upgrade;

import com.liferay.commerce.model.CommerceShippingMethod;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRel;
import com.liferay.commerce.payment.service.CommercePaymentMethodGroupRelLocalService;
import com.liferay.commerce.service.CommerceShippingMethodLocalService;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.image.service.internal.upgrade.v1_0_0.ImageCompanyIdUpgradeProcess;
import com.liferay.image.service.internal.upgrade.v1_0_0.ImageStorageUpgradeProcess;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetBranchLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portlet.documentlibrary.store.StoreFactory;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(immediate = true, service = UpgradeStepRegistrator.class)
public class ImageServiceUpgrade implements UpgradeStepRegistrator {

	@Override
	public void register(Registry registry) {
		registry.register(
			"0.0.0", "1.0.0",
			new ImageCompanyIdUpgradeProcess<>(
				_commercePaymentMethodGroupRelLocalService::
					getActionableDynamicQuery,
				CommercePaymentMethodGroupRel::getCompanyId,
				CommercePaymentMethodGroupRel::getImageId),
			new ImageCompanyIdUpgradeProcess<>(
				_commerceShippingMethodLocalService::getActionableDynamicQuery,
				CommerceShippingMethod::getCompanyId,
				CommerceShippingMethod::getImageId),
			new ImageCompanyIdUpgradeProcess<>(
				_companyLocalService::getActionableDynamicQuery,
				Company::getCompanyId, Company::getLogoId),
			new ImageCompanyIdUpgradeProcess<>(
				_ddmTemplateLocalService::getActionableDynamicQuery,
				DDMTemplate::getCompanyId, DDMTemplate::getSmallImageId),
			new ImageCompanyIdUpgradeProcess<>(
				_journalArticleLocalService::getActionableDynamicQuery,
				JournalArticle::getCompanyId, JournalArticle::getSmallImageId),
			new ImageCompanyIdUpgradeProcess<>(
				_layoutLocalService::getActionableDynamicQuery,
				Layout::getCompanyId, Layout::getIconImageId),
			new ImageCompanyIdUpgradeProcess<>(
				_layoutSetLocalService::getActionableDynamicQuery,
				LayoutSet::getCompanyId, LayoutSet::getLogoId),
			new ImageCompanyIdUpgradeProcess<>(
				_layoutSetBranchLocalService::getActionableDynamicQuery,
				LayoutSetBranch::getCompanyId, LayoutSetBranch::getLogoId),
			new ImageCompanyIdUpgradeProcess<>(
				_layoutSetBranchLocalService::getActionableDynamicQuery,
				LayoutSetBranch::getCompanyId, LayoutSetBranch::getLiveLogoId),
			new ImageStorageUpgradeProcess(_imageLocalService, _storeFactory));
	}

	@Reference
	private CommercePaymentMethodGroupRelLocalService
		_commercePaymentMethodGroupRelLocalService;

	@Reference
	private CommerceShippingMethodLocalService
		_commerceShippingMethodLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;

	@Reference
	private ImageLocalService _imageLocalService;

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutSetBranchLocalService _layoutSetBranchLocalService;

	@Reference
	private LayoutSetLocalService _layoutSetLocalService;

	@Reference(target = "(dl.store.impl.enabled=true)")
	private StoreFactory _storeFactory;

}