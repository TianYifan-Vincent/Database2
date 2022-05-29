package com.shu.carsystem.mapper;

import com.shu.carsystem.entity.Client;
import com.shu.carsystem.entity.Maintain;
import com.shu.carsystem.entity.Project;
import com.shu.carsystem.pojo.MaintainInfo1;
import com.shu.carsystem.pojo.MaterialDetail;
import com.shu.carsystem.pojo.NotEnsuredRepair;
import com.shu.carsystem.pojo.RepairmanRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Mapper
public interface MaintainMapper {

    int insertMaintain(Maintain maintain);

    List<NotEnsuredRepair> getNotEnsuredRepairs(@Param("userId") Integer userId,@Param("keyWord") String keyWord);

    List<NotEnsuredRepair> getFinishedRepairs(@Param("userId") Integer userId,@Param("keyWord") String keyWord);

    int affirmMaintain(@Param("repairmanRecord") RepairmanRecord repairmanRecord);

    RepairmanRecord getRepairInfoByRepairmanId(@Param("userId") Integer userId);

    List<Project> getOngoingProjectList(@Param("repairId") Integer repairId, @Param("repairmanId") Integer repairmanId);

    List<MaterialDetail> getMaterialDetail(@Param("repairId") Integer repairId,@Param("repairmanId") Integer repairmanId,@Param("proId") Integer proId);

    int setMaterialNumAddNumber(@Param("repairId") Integer repairId,@Param("repairmanId") Integer repairmanId,@Param("proId") Integer proId,@Param("number") Integer number, @Param("matId") Integer matId);

    int addNewDetail(@Param("repairId") Integer repairId,@Param("repairmanId") Integer repairmanId,@Param("proId") Integer proId,@Param("number") Integer number, @Param("matId") Integer matId);

    int finishMaintain(@Param("repairId") Integer repairId,@Param("repairmanId") Integer repairmanId,@Param("proId") Integer proId);

    List<MaintainInfo1> getMaintainInfo1ByRid(Integer repairId);
}
