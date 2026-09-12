package ru.teplayakompaniya.tk4;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.text.NumberFormat;
import java.util.Locale;

public class MainActivity extends Activity {
    private final int GREEN = Color.rgb(10,122,75);
    private final int ORANGE = Color.rgb(255,122,22);
    private final int BLUE = Color.rgb(52,120,229);
    private final int RED = Color.rgb(244,63,78);
    private final int INK = Color.rgb(15,31,54);
    private final int MUTED = Color.rgb(108,122,144);
    private final int LINE = Color.rgb(230,236,243);
    private final int BG = Color.rgb(247,249,251);
    private FrameLayout content;

    @Override public void onCreate(Bundle b) {
        super.onCreate(b);
        getWindow().setStatusBarColor(Color.WHITE);
        getWindow().setNavigationBarColor(Color.WHITE);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        setContentView(buildRoot());
        showMain();
    }

    private View buildRoot() {
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setBackgroundColor(BG);
        content = new FrameLayout(this);
        root.addView(content, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0,1));
        root.addView(buildBottomNav(), new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(72)));
        return root;
    }

    private View buildBottomNav() {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setGravity(Gravity.CENTER);
        row.setPadding(dp(4),dp(4),dp(4),dp(4));
        row.setBackgroundColor(Color.WHITE);
        row.setElevation(dp(8));
        row.addView(navItem("⌂\nГлавная", v -> showMain()), weight());
        row.addView(navItem("▣\nПлан", v -> showCalendar()), weight());
        TextView plus = navItem("＋\nДобавить", v -> showCreateObject()); plus.setTextColor(GREEN); plus.setTextSize(17);
        row.addView(plus, weight());
        row.addView(navItem("♧\nУведомления", v -> toast("Центр уведомлений — следующий этап")), weight());
        row.addView(navItem("◯\nПрофиль", v -> showSettings()), weight());
        return row;
    }

    private LinearLayout.LayoutParams weight(){ return new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1); }

    private TextView navItem(String s, View.OnClickListener l){
        TextView t=text(s,11,MUTED,false); t.setGravity(Gravity.CENTER); t.setOnClickListener(l); t.setPadding(dp(2),0,dp(2),0); return t;
    }

    private void setPage(View v){ content.removeAllViews(); content.addView(v); }

    private ScrollView pageScroll(){ ScrollView s=new ScrollView(this); s.setFillViewport(true); s.setBackgroundColor(BG); return s; }
    private LinearLayout column(){ LinearLayout l=new LinearLayout(this); l.setOrientation(LinearLayout.VERTICAL); l.setPadding(dp(14),dp(10),dp(14),dp(22)); return l; }

    private void showMain(){
        ScrollView sv=pageScroll(); LinearLayout c=column(); sv.addView(c);
        c.addView(header());
        c.addView(periodRow());
        c.addView(two(kpi("Оборот","1 245 000 ₽","↑ +12%",0xFFEEFBF4,GREEN,null), kpi("Прибыль","425 000 ₽","↑ +18%",0xFFEEF6FF,BLUE,null)));
        c.addView(two(kpi("Монтажи","8","↑ +14%",0xFFFFF5EA,ORANGE,null), kpi("Объекты в работе","11","↑ +22%",0xFFEFFBF7,GREEN,v->showObjects())));
        c.addView(sectionTitle("Ключевые показатели","Этот месяц⌄"));
        c.addView(three(mini("Лиды","42"), mini("Замеры","20"), mini("Договоры","11")));
        c.addView(three(mini("Средний чек","113 000 ₽"), mini("Дебиторка","320 000 ₽"), mini("Расходы","820 000 ₽")));
        c.addView(sectionTitle("Быстрый доступ",""));
        c.addView(four(appTile("⌂","Объекты",v->showObjects()),appTile("♟","Монтажники",v->showInstallers()),appTile("⚒","Инженеры",v->toast("Инженер — следующий контур")),appTile("♣","Менеджеры",v->toast("Менеджер — следующий контур"))));
        c.addView(four(appTile("₽","Финансы",v->showFinance()),appTile("▥","Аналитика",v->showAnalytics()),appTile("▦","Календарь",v->showCalendar()),appTile("⚙","Настройки",v->showSettings())));
        c.addView(sectionTitle("Сегодня требует внимания","Все уведомления ›"));
        c.addView(alert("!","Просрочена задача","Объект ул. Лесная, 12 — не выполнена проверка",RED));
        c.addView(alert("▥","Низкая конверсия","Замеры → Договоры: 20%",ORANGE));
        c.addView(alert("₽","Есть неоплаченные счета","Дебиторская задолженность: 320 000 ₽",RED));
        setPage(sv);
    }

    private View header(){
        LinearLayout wrap=column(); wrap.setPadding(0,0,0,0); wrap.setBackgroundColor(BG);
        LinearLayout top=new LinearLayout(this); top.setGravity(Gravity.CENTER_VERTICAL);
        ImageView logo=new ImageView(this); logo.setImageResource(ru.teplayakompaniya.tk4.R.drawable.logo_mark); top.addView(logo,new LinearLayout.LayoutParams(dp(48),dp(48)));
        LinearLayout brand=new LinearLayout(this); brand.setOrientation(LinearLayout.VERTICAL); brand.setPadding(dp(8),0,0,0);
        brand.addView(text("ТЁПЛАЯ КОМПАНИЯ",16,INK,true)); brand.addView(text("Строим тепло вместе",11,MUTED,false));
        top.addView(brand,new LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT,1));
        top.addView(text("♧",24,INK,false),new LinearLayout.LayoutParams(dp(48),dp(48)));
        wrap.addView(top);
        LinearLayout hello=new LinearLayout(this); hello.setGravity(Gravity.CENTER_VERTICAL); hello.setPadding(0,dp(8),0,dp(10));
        TextView av=text("ИИ",16,Color.WHITE,true); av.setGravity(Gravity.CENTER); av.setBackground(round(GREEN,99)); hello.addView(av,new LinearLayout.LayoutParams(dp(54),dp(54)));
        LinearLayout h=new LinearLayout(this); h.setOrientation(LinearLayout.VERTICAL); h.setPadding(dp(10),0,0,0);
        h.addView(text("Добрый день,",12,MUTED,false)); h.addView(text("Игорь Игоревич",20,INK,true)); h.addView(text("Руководитель  ·  10 сентября 2026",11,MUTED,false));
        hello.addView(h); wrap.addView(hello); return wrap;
    }

    private View periodRow(){ LinearLayout r=new LinearLayout(this); r.setPadding(dp(4),dp(4),dp(4),dp(4)); r.setBackground(round(0xFFF2F5F8,18)); String[] p={"Сегодня","Неделя","Месяц","Квартал","Год"}; for(String s:p){ TextView t=text(s,10,s.equals("Месяц")?Color.WHITE:MUTED,s.equals("Месяц")); t.setGravity(Gravity.CENTER); if(s.equals("Месяц")) t.setBackground(round(GREEN,14)); r.addView(t,new LinearLayout.LayoutParams(0,dp(42),1)); } LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2); lp.setMargins(0,0,0,dp(10)); r.setLayoutParams(lp); return r; }

    private View kpi(String title,String value,String delta,int bg,int accent,View.OnClickListener l){ LinearLayout c=card(bg); c.addView(text(title,12,INK,false)); c.addView(text(value,20,INK,true)); TextView d=text(delta,12,accent,true); d.setPadding(0,dp(5),0,0); c.addView(d); if(l!=null)c.setOnClickListener(l); return c; }
    private View mini(String a,String b){ LinearLayout c=card(Color.WHITE); c.addView(text(a,10,MUTED,false)); c.addView(text(b,15,INK,true)); return c; }
    private View appTile(String ico,String label,View.OnClickListener l){ LinearLayout c=card(Color.WHITE); c.setGravity(Gravity.CENTER); TextView i=text(ico,23,GREEN,true); i.setGravity(Gravity.CENTER); c.addView(i); TextView b=text(label,10,INK,true); b.setGravity(Gravity.CENTER); c.addView(b); c.setOnClickListener(l); return c; }

    private View sectionTitle(String a,String b){ LinearLayout r=new LinearLayout(this); r.setGravity(Gravity.CENTER_VERTICAL); r.setPadding(0,dp(15),0,dp(7)); r.addView(text(a,18,INK,true),new LinearLayout.LayoutParams(0,-2,1)); r.addView(text(b,11,MUTED,false)); return r; }
    private View alert(String ico,String title,String sub,int color){ LinearLayout r=card(Color.WHITE); r.setOrientation(LinearLayout.HORIZONTAL); r.setGravity(Gravity.CENTER_VERTICAL); TextView i=text(ico,16,Color.WHITE,true); i.setGravity(Gravity.CENTER); i.setBackground(round(color,99)); r.addView(i,new LinearLayout.LayoutParams(dp(34),dp(34))); LinearLayout t=new LinearLayout(this); t.setOrientation(LinearLayout.VERTICAL); t.setPadding(dp(10),0,0,0); t.addView(text(title,13,INK,true)); t.addView(text(sub,11,MUTED,false)); r.addView(t); return r; }

    private void showObjects(){
        ScrollView sv=pageScroll(); LinearLayout c=column(); sv.addView(c); c.addView(innerHeader("Объекты","Все объекты компании"));
        c.addView(two(kpi("В работе","6","",Color.WHITE,GREEN,null),kpi("Запланированы","18","",Color.WHITE,BLUE,null)));
        c.addView(two(kpi("Подтверждены","7","",Color.WHITE,ORANGE,null),kpi("Замеры за месяц","42","В монтаж: 11",Color.WHITE,GREEN,null)));
        c.addView(sectionTitle("Активные объекты","＋ Новый объект"));
        c.addView(objectCard("Мытищи, ул. Лесная, 12","Сидоров А.В.","Утепление фасада","В работе · 65%"));
        c.addView(objectCard("Королёв, ул. Полевая, 7","Петрова Е.С.","Утепление","Запланирован · 15.09"));
        c.addView(objectCard("Пушкино, СНТ Берёзка","Иванов М.С.","Тёплый пол","В работе · 25%"));
        setPage(sv);
    }
    private View objectCard(String a,String client,String type,String status){ LinearLayout c=card(Color.WHITE); c.addView(text(a,14,INK,true)); c.addView(text(client+" · "+type,11,MUTED,false)); TextView s=text(status,11,GREEN,true); s.setPadding(0,dp(6),0,0); c.addView(s); return c; }

    private void showCreateObject(){
        ScrollView sv=pageScroll(); LinearLayout c=column(); sv.addView(c); c.addView(innerHeader("Создать объект","Новый объект в системе"));
        c.addView(info("ДЕМО","Сохранение в Google Sheets подключим на следующем техническом этапе."));
        c.addView(field("Клиент","Иванов Сергей Петрович")); c.addView(field("Телефон","+7 915 123-45-67")); c.addView(field("Адрес объекта","Химки, ул. Лесная, 12")); c.addView(field("Вид работ","Комплексное утепление")); c.addView(field("Сумма договора","350000")); c.addView(field("Дата замера","10.09.2026")); c.addView(field("Плановая дата монтажа","25.09.2026")); c.addView(field("Статус","Подтверждён клиентом"));
        Button save=new Button(this); save.setText("Сохранить объект"); save.setTextColor(Color.WHITE); save.setBackground(round(GREEN,14)); save.setOnClickListener(v->toast("Объект сохранён в DEMO")); c.addView(save,new LinearLayout.LayoutParams(-1,dp(54)));
        setPage(sv);
    }
    private View field(String label,String val){ LinearLayout w=new LinearLayout(this); w.setOrientation(LinearLayout.VERTICAL); w.setPadding(0,dp(5),0,dp(5)); w.addView(text(label,11,MUTED,true)); EditText e=new EditText(this); e.setText(val); e.setTextColor(INK); e.setTextSize(14); e.setSingleLine(true); e.setBackground(round(Color.WHITE,13)); e.setPadding(dp(12),0,dp(12),0); w.addView(e,new LinearLayout.LayoutParams(-1,dp(50))); return w; }

    private void showInstallers(){ ScrollView sv=pageScroll(); LinearLayout c=column(); sv.addView(c); c.addView(innerHeader("Монтажники","Аналитика, выплаты и имущество")); c.addView(two(kpi("Монтажников","8","",0xFFFFF5EA,ORANGE,null),kpi("На объектах","6","",0xFFEFFBF7,GREEN,null))); c.addView(two(kpi("Начислено","524 000 ₽","",0xFFEEF6FF,BLUE,null),kpi("К выдаче","344 000 ₽","",0xFFFFF5EA,ORANGE,null))); c.addView(sectionTitle("Сотрудники","")); c.addView(worker("Алексей Смирнов","22 раб. дня · 4 объекта","К выдаче 62 000 ₽")); c.addView(worker("Илья Орлов","20 раб. дней · 3 объекта","К выдаче 74 000 ₽")); c.addView(worker("Сергей Плотников","18 раб. дней · 2 объекта","К выдаче 56 000 ₽")); setPage(sv); }
    private View worker(String n,String m,String money){ LinearLayout c=card(Color.WHITE); c.addView(text(n,14,INK,true)); c.addView(text(m,11,MUTED,false)); c.addView(text(money,12,GREEN,true)); c.addView(text("Инструмент: 3 ед. · Форма: учёт выдачи",10,MUTED,false)); return c; }

    private void showFinance(){ ScrollView sv=pageScroll(); LinearLayout c=column(); sv.addView(c); c.addView(innerHeader("Финансы","Приходы, расходы и живые остатки")); c.addView(two(kpi("Оборот","2 480 000 ₽","",0xFFEEFBF4,GREEN,null),kpi("Расходы","1 860 000 ₽","",0xFFFFF5EA,ORANGE,null))); c.addView(two(kpi("Общий остаток","1 320 000 ₽","",0xFFEEF6FF,BLUE,null),kpi("Подотчёт","806 000 ₽","",0xFFEFFBF7,GREEN,null))); c.addView(sectionTitle("Остатки","")); c.addView(two(mini("Игорь","420 000 ₽"),mini("Константин","386 000 ₽"))); c.addView(sectionTitle("Операции","")); c.addView(tx("+350 000 ₽","Оплата по договору","ул. Лесная, 12",GREEN)); c.addView(tx("−125 000 ₽","Покупка материалов","ул. Садовая, 8",RED)); c.addView(tx("50 000 ₽","Константин → Игорь","Внутренний перевод",BLUE)); setPage(sv); }
    private View tx(String sum,String title,String sub,int color){ LinearLayout c=card(Color.WHITE); LinearLayout r=new LinearLayout(this); r.setGravity(Gravity.CENTER_VERTICAL); LinearLayout t=new LinearLayout(this); t.setOrientation(LinearLayout.VERTICAL); t.addView(text(title,13,INK,true)); t.addView(text(sub,10,MUTED,false)); r.addView(t,new LinearLayout.LayoutParams(0,-2,1)); r.addView(text(sum,13,color,true)); c.addView(r); return c; }

    private void showAnalytics(){ ScrollView sv=pageScroll(); LinearLayout c=column(); sv.addView(c); c.addView(innerHeader("Аналитика","Объекты, поступления и дебиторка")); c.addView(two(kpi("Оборот","2 480 000 ₽","",0xFFEEFBF4,GREEN,null),kpi("Дебиторка","680 000 ₽","",0xFFFFF5EA,ORANGE,null))); c.addView(two(kpi("План поступлений","1 120 000 ₽","",0xFFEEF6FF,BLUE,null),kpi("Осталось получить","1 800 000 ₽","",0xFFEFFBF7,GREEN,null))); c.addView(sectionTitle("По объектам","")); c.addView(analyticsCard("Химки, ул. Лесная, 12","Договор 1 200 000 ₽ · Получено 800 000 ₽","План 400 000 ₽ · Дебиторка 0 ₽")); c.addView(analyticsCard("Королёв, ул. Полевая, 7","Договор 600 000 ₽ · Получено 400 000 ₽","Дебиторка 200 000 ₽ · просрочка 4 дня")); setPage(sv); }
    private View analyticsCard(String a,String b,String d){ LinearLayout c=card(Color.WHITE); c.addView(text(a,14,INK,true)); c.addView(text(b,11,MUTED,false)); c.addView(text(d,11,d.contains("200 000")?RED:GREEN,true)); return c; }

    private void showCalendar(){ ScrollView sv=pageScroll(); LinearLayout c=column(); sv.addView(c); c.addView(innerHeader("Календарь","Загрузка монтажников и объектов")); c.addView(info("15 сентября","Смирнов + Орлов → Химки, Лесная, 12\nПлотников → свободен\nКрылов → выходной")); c.addView(info("Контроль конфликтов","При назначении одного монтажника на пересекающиеся даты приложение покажет предупреждение.")); setPage(sv); }
    private void showSettings(){ ScrollView sv=pageScroll(); LinearLayout c=column(); sv.addView(c); c.addView(innerHeader("Настройки","Профиль и приложение")); c.addView(info("Профиль","Фото из камеры/галереи · ФИО · телефон · должность")); c.addView(info("Тема","Светлая · Тёмная · Как на устройстве")); c.addView(info("Уведомления","Объекты · ТЗ · платежи · отчёты · просрочки")); c.addView(info("Версия","4.0.0-alpha4 · Native stability build")); setPage(sv); }

    private View innerHeader(String title,String sub){ LinearLayout r=new LinearLayout(this); r.setGravity(Gravity.CENTER_VERTICAL); TextView back=text("‹",30,INK,false); back.setGravity(Gravity.CENTER); back.setBackground(round(Color.WHITE,14)); back.setOnClickListener(v->showMain()); r.addView(back,new LinearLayout.LayoutParams(dp(46),dp(46))); LinearLayout t=new LinearLayout(this); t.setOrientation(LinearLayout.VERTICAL); t.setPadding(dp(10),0,0,0); t.addView(text(title,22,INK,true)); t.addView(text(sub,11,MUTED,false)); r.addView(t); LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2); lp.setMargins(0,0,0,dp(10)); r.setLayoutParams(lp); return r; }
    private View info(String title,String body){ LinearLayout c=card(Color.WHITE); c.addView(text(title,14,INK,true)); TextView b=text(body,11,MUTED,false); b.setPadding(0,dp(5),0,0); c.addView(b); return c; }

    private LinearLayout card(int color){ LinearLayout c=new LinearLayout(this); c.setOrientation(LinearLayout.VERTICAL); c.setPadding(dp(12),dp(12),dp(12),dp(12)); c.setBackground(round(color,18)); c.setElevation(dp(1)); LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,-2); lp.setMargins(0,dp(4),0,dp(4)); c.setLayoutParams(lp); return c; }
    private View two(View a,View b){ LinearLayout r=new LinearLayout(this); r.setOrientation(LinearLayout.HORIZONTAL); addWeighted(r,a,1); addWeighted(r,b,1); return r; }
    private View three(View a,View b,View c){ LinearLayout r=new LinearLayout(this); addWeighted(r,a,1); addWeighted(r,b,1); addWeighted(r,c,1); return r; }
    private View four(View a,View b,View c,View d){ LinearLayout r=new LinearLayout(this); addWeighted(r,a,1); addWeighted(r,b,1); addWeighted(r,c,1); addWeighted(r,d,1); return r; }
    private void addWeighted(LinearLayout r,View v,float w){ LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(0,-2,w); lp.setMargins(dp(3),dp(3),dp(3),dp(3)); v.setLayoutParams(lp); r.addView(v); }
    private TextView text(String s,float size,int color,boolean bold){ TextView t=new TextView(this); t.setText(s); t.setTextSize(size); t.setTextColor(color); if(bold)t.setTypeface(Typeface.DEFAULT,Typeface.BOLD); t.setGravity(Gravity.START|Gravity.CENTER_VERTICAL); return t; }
    private GradientDrawable round(int color,float radius){ GradientDrawable d=new GradientDrawable(); d.setColor(color); d.setCornerRadius(dp(radius)); d.setStroke(dp(1),LINE); return d; }
    private int dp(float v){ return (int)(v*getResources().getDisplayMetrics().density+0.5f); }
    private void toast(String s){ Toast.makeText(this,s,Toast.LENGTH_SHORT).show(); }
}
