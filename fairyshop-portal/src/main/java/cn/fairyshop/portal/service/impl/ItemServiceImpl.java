package cn.fairyshop.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.fairyshop.common.pojo.FSResult;
import cn.fairyshop.common.utils.HttpClientUtils;
import cn.fairyshop.common.utils.JsonUtils;
import cn.fairyshop.pojo.TbItem;
import cn.fairyshop.pojo.TbItemDesc;
import cn.fairyshop.pojo.TbItemParamItem;
import cn.fairyshop.portal.pojo.PortalItem;
import cn.fairyshop.portal.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_ITEM_BASE_URL}")
	private String REST_ITEM_BASE_URL;
	@Value("${REST_ITEM_DESC_URL}")
	private String REST_ITEM_DESC_URL;
	@Value("${REST_ITEM_PARAM_URL}")
	private String REST_ITEM_PARAM_URL;

	@Override
	public TbItem getItemById(Long itemId) {
		// TODO Auto-generated method stub
		String url = REST_BASE_URL + REST_ITEM_BASE_URL + itemId;
		String json = HttpClientUtils.doGet(url);
		TbItem item = (TbItem) FSResult.formatToPojo(json, PortalItem.class).getData();
		return item;
	}

	@Override
	public String getItemDescById(Long itemId) {
		// TODO Auto-generated method stub
		String url = REST_BASE_URL + REST_ITEM_DESC_URL + itemId;
		String json = HttpClientUtils.doGet(url);
		TbItemDesc itemDesc = (TbItemDesc) FSResult.formatToPojo(json, TbItemDesc.class).getData();
		return itemDesc.getItemDesc();
	}

	@Override
	public String getItemParamItemById(Long itemId) {
		// TODO Auto-generated method stub
		String url = REST_BASE_URL + REST_ITEM_PARAM_URL + itemId;
		String json = HttpClientUtils.doGet(url);
		TbItemParamItem itemParamItem = (TbItemParamItem) FSResult.formatToPojo(json, TbItemParamItem.class).getData();
		List<Map> mapList = JsonUtils.jsonToList(itemParamItem.getParamData(), Map.class);

		String html = "";
		if (mapList != null && mapList.size() > 0) {
			// 遍历map，构建html
			StringBuffer sb = new StringBuffer();

			sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">");
			sb.append("  <tbody>");
			for (Map map : mapList) {
				// 构建组标题
				sb.append("    <tr>");
				sb.append("      <th class=\"tdTitle\"  colspan=\"2\">" + map.get("group") + "</th>");
				sb.append("    </tr>");

				// 构建各个参数标题
				List<Map> mapList2 = (List<Map>) map.get("params");
				for (Map map2 : mapList2) {
					sb.append("    <tr>");
					sb.append("      <td class=\"tdTitle\">" + map2.get("k") + "</td>");
					sb.append("      <td>" + map2.get("v") + "</td>");
					sb.append("    </tr>");
				}

			}
			sb.append("  </tbody>");
			sb.append("</table>");

			html = sb.toString();

		}

		return html;
	}

}
