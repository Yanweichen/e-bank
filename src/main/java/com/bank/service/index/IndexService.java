package com.bank.service.index;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.base.BaseService;
import com.bank.dao.index.IndexDAO;
import com.bank.model.index.IndexModel;
import com.bank.model.index.LabelModel;
import com.bank.model.other.Page;

/**
 * @author yanwe
 * 首页服务层
 */
@Service
public class IndexService implements BaseService<IndexModel> {

	@Autowired
	private IndexDAO indexdao;
	
	/**
	 * 添加到首页表
	 * @param im
	 * @return
	 */
	public int removeByIdView(int id){
		return indexdao.deleteByIdView(id);
	}
	
	/**
	 * 添加到首页表
	 * @param im
	 * @return
	 */
	public int add2View(int id){
		return indexdao.insertView(id);
	}
	
	public int findCountByType(Integer pid,String isView){
		return indexdao.selectCountByType(pid,isView);
	}
	/**
	 * 分页
	 * @param page
	 * @return
	 */
	public List<IndexModel> findeByPage(Page page){
		return indexdao.selectByPage(page);
	}
	
	public IndexModel findTopByState(Integer state){
		return indexdao.selectTopByState(state);
	}
	
	public IndexModel findByBoA(String ba,int id){
		return indexdao.selectOneByBeforeOrAfter(ba, id);
	}
	/**
	 * 根据id增加点击量
	 * @param id
	 * @return
	 */
	public int addHits(int id){
		return indexdao.updateHitsNum(indexdao.seletHitsNumById(id), id);
	}
	
	/**
	 * 根据标签查询相关文章
	 * @param label
	 * @param num
	 * @return
	 */
	public List<IndexModel> findAboutByLabel(Page page){
		if (page.getSearch()==null||"".equals(page.getSearch())) {
			return indexdao.selectAboutByLabel(page,"NULL");
		} else {
			String [] labels = page.getSearch().split(",");
			StringBuilder labelssb = new StringBuilder();
			for (String string : labels) {
				labelssb.append("'"+string+"',");
			}
			return indexdao.selectAboutByLabel(page,labelssb.substring(0,labelssb.length()-1).toString());
		}
	}
	/**
	 * 根据标签查询相关文章
	 * @param label
	 * @param num
	 * @return
	 */
	public int findAboutByLabelCount(Page page){
		if (page.getSearch()==null||"".equals(page.getSearch())) {
			page.setSearch("NULL");
			return indexdao.selectAboutByLabelCount(page.getSearch());
		} else {
			String [] labels = page.getSearch().split(",");
			StringBuilder labelssb = new StringBuilder();
			for (String string : labels) {
				labelssb.append("'"+string+"',");
			}
			return indexdao.selectAboutByLabelCount(labelssb.substring(0,labelssb.length()-1).toString());
		}
	}
	
	public List<LabelModel> findAllLabel(){
		return indexdao.selectAllLabel();
	}
	public List<LabelModel> findeHotLabel(Integer num){
		return indexdao.selectHotLabel(num);
	}
	
	@Override
	public int add(IndexModel model) {
		// TODO Auto-generated method stub
		try {
			int pk = indexdao.insert(model);
			indexdao.insertLabel(model.getIndex_id(), model.getIndex_label());
			return pk;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	
	@Override
	public int RemoveById(Integer id) {
		// TODO Auto-generated method stub
		return indexdao.deleteById(id);
	}


	@Override
	public IndexModel findById(Integer id) {
		// TODO Auto-generated method stub
		return indexdao.selectById(id);
	}

	@Override
	public List<IndexModel> findAll() {
		// TODO Auto-generated method stub
		return indexdao.selectAll();
	}
	@Override
	public int alterById(IndexModel model) {
		// TODO Auto-generated method stub
		return indexdao.updateById(model);
	}
	
}
