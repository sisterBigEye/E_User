package com.hlbd.electric.feature.launcher.data.analysis.power;


import com.hlbd.electric.base.BasePresenter;
import com.hlbd.electric.base.BaseView;
import com.hlbd.electric.feature.launcher.data.analysis.power.data.PoweTodayRealTimePData;
import com.hlbd.electric.feature.launcher.data.analysis.power.data.Power30EleData;
import com.hlbd.electric.feature.launcher.data.analysis.power.data.Power7DayTimeEPData;
import com.hlbd.electric.feature.launcher.data.analysis.power.data.PowerCo2CoalData;
import com.hlbd.electric.feature.launcher.data.analysis.power.data.PowerData;
import com.hlbd.electric.feature.launcher.data.analysis.power.data.PowerLastMonthEPDividerData;
import com.hlbd.electric.feature.launcher.data.analysis.power.data.PowerLastMonthEPTotalData;
import com.hlbd.electric.feature.launcher.data.analysis.power.data.PowerMonthEPPriceData;
import com.hlbd.electric.feature.launcher.data.analysis.power.data.PowerMonthMaxPTrendData;
import com.hlbd.electric.feature.launcher.data.analysis.power.data.PowerPMaxData;

class PowerContract {

  interface Presenter extends BasePresenter {

    void loadPowerData();

    void load30DaysEleData();

    void getCO2AndCoalUsageByUser();

    void getLastMonthEPDivide();

    void getLastMonthEPTotal();

    void getMonthEPPrice();

    void getMonthMaxPTrend();

    void getTodayRealTimeP();

    void get7DayTimeEP();

    void getPMax();

  }

  interface View extends BaseView<Presenter> {

    void updatePowerData(PowerData data);

    void update30DaysEleData(Power30EleData data);

    void co2CoalResult(PowerCo2CoalData data);

    void lastMonthEPDivideResult(PowerLastMonthEPDividerData data);

    void lastMonthEPTotalResult(PowerLastMonthEPTotalData data);

    void monthEPPriceResult(PowerMonthEPPriceData data);

    void monthMaxPTrendResult(PowerMonthMaxPTrendData data);

    void todayRealTimePResult(PoweTodayRealTimePData data);

    void d7DayTimeEPResult(Power7DayTimeEPData data);

    void pMaxResult(PowerPMaxData data);

  }

}
