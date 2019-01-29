/*
 * Copyright (C) 2014 Dashman
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package srw.mxp.script.editor;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jonatan
 */
public class Dialogue {
    String original;
    String edited;
    String converted;
    boolean skip;
    
    String font_encoding = "MS932";
    
    private static Map<String, String> map; // Shared among all instances of Dialogue
    
    
    public Dialogue(String o, String e, String c, boolean s){
        original = o;
        edited = e;
        converted = c;
        skip = s;
    }
    
    
    public Dialogue(){
        original = "";
        edited = "";
        converted = "";
        skip = false;
    }
    
    
    // Obtain the dialogue as an array of bytes (for insertion in a file)
    // If use_converted is true, we give the bytes of converted,
    // otherwise we give the ones of edited.
    public byte[] getBytes(boolean use_converted){
        byte[] return_array = null;
        String chosen_text;
        
        if (use_converted)
            chosen_text = converted;
        else
            chosen_text = edited;        
        
        String[] lines = chosen_text.split("\n");
        
        byte[] separator = new byte[1];
        byte[] end_delimiter = new byte[1]; // Defaults to 0
        separator[0] = 0x40;    // '@' in ASCII
        
        ByteArrayOutputStream os = new ByteArrayOutputStream( );
        try {
            for (int i = 0; i < lines.length; i++){
                os.write(lines[i].getBytes(font_encoding));
                
                if (i < lines.length - 1)
                    os.write(separator);
            }
            
            os.write(end_delimiter);

            return_array = os.toByteArray();
        } catch (IOException ex) {
            Logger.getLogger(Dialogue.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return return_array;
    }
    
    
    // Every 2 ASCII characters in the edited text get converted to 1 Kanji character
    public void convertLetterPairs(){
        converted = ""; // Clean the converted text
        
        //String temp = "" + edited;
        
        String[] lines = edited.split("\n");
        
        for (int i = 0; i < lines.length; i++){
            if(lines[i].length() % 2 != 0)  // Ensure the text has an even number of characters
                lines[i] += " ";
            
            String temp_line = "";

            for (int j = 0; j < lines[i].length(); j += 2){
                String pair = lines[i].substring(j, j+2);

                //temp_line += letterPair2Kanji(pair);
                String kanji = map.get(pair);
                
                if ( kanji == null )
                    temp_line += pair;
                else
                    temp_line += kanji;
            }
            
            converted += temp_line.trim();  // If there was an empty space in the end, we get rid of it
            
            if (i < (lines.length - 1) )
                converted += "\n";
        }
    }
    
    
    // Fill the Map / Dictionary with the letter pairs and its associated kanji
    public void initMap(){
        map = new TreeMap();
                
        // ---------------------- A
        map.put("Aa", "亜");
        map.put("Ab", "唖");
        map.put("Ac", "娃");

        map.put("Ad", "阿");
        map.put("Ae", "哀");
        map.put("Af", "愛");
        map.put("Ag", "挨");
        map.put("Ah", "姶");
        map.put("Ai", "逢");
        map.put("Aj", "葵");
        map.put("Ak", "茜");
        map.put("Al", "穐");
        map.put("Am", "悪");
        map.put("An", "握");
        map.put("Ao", "渥");
        map.put("Ap", "旭");
        map.put("Aq", "葦");

        map.put("Ar", "芦");
        map.put("As", "鯵");
        map.put("At", "梓");
        map.put("Au", "圧");
        map.put("Av", "斡");
        map.put("Aw", "扱");
        map.put("Ax", "宛");
        map.put("Ay", "姐");
        map.put("Az", "虻");

        map.put("A.", "湘");
        map.put("A,", "譲");
        map.put("A!", "刃");
        map.put("A?", "瀬");
        map.put("A ", "碩");
        map.put("A-", "槽");
        map.put("AR", "糟");
        map.put("AC", "蒼");
        map.put("AB", "族");
        map.put("A'", "堕");
        map.put("A:", "鐸");


        // ---------------------- B
        map.put("Ba", "意");
        map.put("Bb", "慰");
        map.put("Bc", "易");
        map.put("Bd", "椅");
        map.put("Be", "為");
        map.put("Bf", "畏");
        map.put("Bg", "異");


        map.put("Bh", "移");
        map.put("Bi", "維");
        map.put("Bj", "緯");
        map.put("Bk", "胃");
        map.put("Bl", "萎");
        map.put("Bm", "衣");
        map.put("Bn", "謂");
        map.put("Bo", "違");
        map.put("Bp", "遺");
        map.put("Bq", "医");
        map.put("Br", "井");
        map.put("Bs", "亥");
        map.put("Bt", "域");
        map.put("Bu", "育");


        map.put("Bv", "郁");
        map.put("Bw", "磯");
        map.put("Bx", "一");
        map.put("By", "壱");
        map.put("Bz", "溢");

        map.put("B.", "焼");
        map.put("B,", "醸");
        map.put("B!", "塵");
        map.put("B?", "畝");
        map.put("B ", "切");
        map.put("B'", "妥");
        map.put("B:", "濁");
        map.put("B-", "弛");


        // ---------------------- C
        map.put("Ca", "雨");
        map.put("Cb", "卯");
        map.put("Cc", "鵜");
        map.put("Cd", "窺");
        map.put("Ce", "丑");
        map.put("Cf", "碓");
        map.put("Cg", "臼");
        map.put("Ch", "渦");

        map.put("Ci", "嘘");
        map.put("Cj", "唄");
        map.put("Ck", "欝");
        map.put("Cl", "蔚");
        map.put("Cm", "鰻");
        map.put("Cn", "姥");
        map.put("Co", "厩");
        map.put("Cp", "浦");
        map.put("Cq", "瓜");
        map.put("Cr", "閏");
        map.put("Cs", "噂");
        map.put("Ct", "云");
        map.put("Cu", "運");
        map.put("Cv", "雲");

        map.put("Cw", "荏");
        map.put("Cx", "餌");
        map.put("Cy", "叡");
        map.put("Cz", "営");

        map.put("C.", "焦");
        map.put("C,", "錠");
        map.put("C!", "壬");
        map.put("C?", "是");
        map.put("C ", "拙");
        map.put("C(", "遭");
        map.put("C'", "息");
        map.put("C:", "諾");
        map.put("C-", "恥");
        map.put("CS", "漬");


        // ---------------------- D
        map.put("Da", "円");

        map.put("Db", "園");
        map.put("Dc", "堰");
        map.put("Dd", "奄");
        map.put("De", "宴");
        map.put("Df", "延");
        map.put("Dg", "怨");
        map.put("Dh", "掩");
        map.put("Di", "援");
        map.put("Dj", "沿");
        map.put("Dk", "演");

        map.put("Dl", "炎");
        map.put("Dm", "焔");
        map.put("Dn", "煙");
        map.put("Do", "燕");
        map.put("Dp", "猿");
        map.put("Dq", "縁");
        map.put("Dr", "艶");
        map.put("Ds", "苑");
        map.put("Dt", "薗");
        map.put("Du", "遠");
        map.put("Dv", "鉛");
        map.put("Dw", "鴛");
        map.put("Dx", "塩");
        map.put("Dy", "於");

        map.put("Dz", "汚");

        map.put("D.", "照");
        map.put("D,", "嘱");
        map.put("D!", "尋");
        map.put("D?", "凄");
        map.put("D ", "接");
        map.put("DG", "促");
        map.put("D'", "打");
        map.put("D:", "茸");
        map.put("DF", "知");
        map.put("D-", "智");


        // ---------------------- E
        map.put("Ea", "乙");

        map.put("Eb", "俺");
        map.put("Ec", "卸");
        map.put("Ed", "恩");
        map.put("Ee", "温");
        map.put("Ef", "穏");
        map.put("Eg", "音");
        map.put("Eh", "下");
        map.put("Ei", "化");
        map.put("Ej", "仮");
        map.put("Ek", "何");
        map.put("El", "伽");
        map.put("Em", "価");
        map.put("En", "佳");
        map.put("Eo", "加");

        map.put("Ep", "可");
        map.put("Eq", "嘉");
        map.put("Er", "夏");
        map.put("Es", "嫁");
        map.put("Et", "家");
        map.put("Eu", "寡");
        map.put("Ev", "科");
        map.put("Ew", "暇");
        map.put("Ex", "果");
        map.put("Ey", "架");
        map.put("Ez", "歌");

        map.put("E.", "症");
        map.put("E,", "埴");
        map.put("E!", "甚");
        map.put("E?", "制");
        map.put("E ", "摂");
        map.put("EV", "巣");
        map.put("EA", "窓");
        map.put("EN", "草");
        map.put("EW", "荘");
        map.put("ER", "増");
        map.put("E-", "速");
        map.put("E'", "柁");
        map.put("E:", "凧");


        // ---------------------- F
        map.put("Fa", "臥");
        map.put("Fb", "芽");
        map.put("Fc", "蛾");
        map.put("Fd", "賀");
        map.put("Fe", "雅");

        map.put("Ff", "餓");
        map.put("Fg", "駕");
        map.put("Fh", "介");
        map.put("Fi", "会");
        map.put("Fj", "解");
        map.put("Fk", "回");
        map.put("Fl", "塊");
        map.put("Fm", "壊");
        map.put("Fn", "廻");
        map.put("Fo", "快");
        map.put("Fp", "怪");
        map.put("Fq", "悔");
        map.put("Fr", "恢");
        map.put("Fs", "懐");

        map.put("Ft", "戒");
        map.put("Fu", "拐");
        map.put("Fv", "改");

        map.put("Fw", "魁");
        map.put("Fx", "晦");
        map.put("Fy", "械");
        map.put("Fz", "海");

        map.put("F.", "省");
        map.put("F,", "飾");
        map.put("F!", "尽");
        map.put("F?", "勢");
        map.put("F ", "折");
        map.put("FO", "痩");
        map.put("FX", "則");
        map.put("FA", "即");
        map.put("F'", "舵");
        map.put("F:", "蛸");
        map.put("FC", "地");
        map.put("F-", "池");


        // ---------------------- G
        map.put("Ga", "蛙");
        map.put("Gb", "垣");
        map.put("Gc", "柿");
        map.put("Gd", "蛎");
        map.put("Ge", "鈎");
        map.put("Gf", "劃");

        map.put("Gg", "嚇");
        map.put("Gh", "各");
        map.put("Gi", "廓");
        map.put("Gj", "拡");
        map.put("Gk", "撹");
        map.put("Gl", "格");
        map.put("Gm", "核");
        map.put("Gn", "殻");
        map.put("Go", "獲");
        map.put("Gp", "確");
        map.put("Gq", "穫");
        map.put("Gr", "覚");
        map.put("Gs", "角");
        map.put("Gt", "赫");

        map.put("Gu", "較");
        map.put("Gv", "郭");
        map.put("Gw", "閣");
        map.put("Gx", "隔");
        map.put("Gy", "革");
        map.put("Gz", "学");

        map.put("G.", "硝");
        map.put("G,", "拭");
        map.put("G!", "腎");
        map.put("G?", "姓");
        map.put("G ", "設");
        map.put("GE", "相");
        map.put("G'", "楕");
        map.put("G:", "只");
        map.put("G-", "痴");


        // ---------------------- H
        map.put("Ha", "鞄");
        map.put("Hb", "株");
        map.put("Hc", "兜");
        map.put("Hd", "竃");
        map.put("He", "蒲");
        map.put("Hf", "釜");
        map.put("Hg", "鎌");
        map.put("Hh", "噛");
        map.put("Hi", "鴨");

        map.put("Hj", "栢");
        map.put("Hk", "茅");
        map.put("Hl", "萱");
        map.put("Hm", "粥");
        map.put("Hn", "刈");
        map.put("Ho", "苅");
        map.put("Hp", "瓦");
        map.put("Hq", "乾");
        map.put("Hr", "侃");
        map.put("Hs", "冠");
        map.put("Ht", "寒");
        map.put("Hu", "刊");
        map.put("Hv", "勘");
        map.put("Hw", "勧");

        map.put("Hx", "巻");
        map.put("Hy", "喚");
        map.put("Hz", "堪");

        map.put("H.", "礁");
        map.put("H,", "植");
        map.put("H!", "訊");
        map.put("H?", "征");
        map.put("H ", "窃");
        map.put("HP", "聡");
        map.put("H'", "陀");
        map.put("H:", "叩");
        map.put("H-", "稚");


        // ---------------------- I
        map.put("Ia", "管");
        map.put("Ib", "簡");
        map.put("Ic", "緩");
        map.put("Id", "缶");
        map.put("Ie", "翰");
        map.put("If", "肝");
        map.put("Ig", "艦");
        map.put("Ih", "莞");
        map.put("Ii", "観");
        map.put("Ij", "諌");
        map.put("Ik", "貫");
        map.put("Il", "還");
        map.put("Im", "鑑");

        map.put("In", "間");
        map.put("Io", "閑");
        map.put("Ip", "関");
        map.put("Iq", "陥");
        map.put("Ir", "韓");
        map.put("Is", "館");
        map.put("It", "舘");
        map.put("Iu", "丸");
        map.put("Iv", "含");
        map.put("Iw", "岸");
        map.put("Ix", "巌");
        map.put("Iy", "玩");
        map.put("Iz", "癌");

        map.put("I.", "祥");
        map.put("I,", "殖");
        map.put("I!", "迅");
        map.put("I?", "性");
        map.put("I ", "節");
        map.put("I-", "束");
        map.put("I'", "駄");
        map.put("I:", "但");
        map.put("I1", "帖");
        map.put("IT", "鶴");


        // ---------------------- J
        map.put("Ja", "棋");
        map.put("Jb", "棄");

        map.put("Jc", "機");
        map.put("Jd", "帰");
        map.put("Je", "毅");
        map.put("Jf", "気");
        map.put("Jg", "汽");
        map.put("Jh", "畿");
        map.put("Ji", "祈");
        map.put("Jj", "季");
        map.put("Jk", "稀");
        map.put("Jl", "紀");
        map.put("Jm", "徽");
        map.put("Jn", "規");

        map.put("Jo", "記");
        map.put("Jp", "貴");
        map.put("Jq", "起");
        map.put("Jr", "軌");
        map.put("Js", "輝");
        map.put("Jt", "飢");
        map.put("Ju", "騎");
        map.put("Jv", "鬼");
        map.put("Jw", "亀");
        map.put("Jx", "偽");
        map.put("Jy", "儀");
        map.put("Jz", "妓");

        map.put("J.", "称");
        map.put("J,", "燭");
        map.put("J!", "陣");
        map.put("J?", "成");
        map.put("J ", "説");
        map.put("J'", "騨");
        map.put("J:", "達");
        map.put("J-", "置");
        map.put("JU", "爪");


        // ---------------------- K
        map.put("Ka", "脚");
        map.put("Kb", "虐");
        map.put("Kc", "逆");
        map.put("Kd", "丘");

        map.put("Ke", "久");
        map.put("Kf", "仇");
        map.put("Kg", "休");
        map.put("Kh", "及");
        map.put("Ki", "吸");
        map.put("Kj", "宮");
        map.put("Kk", "弓");
        map.put("Kl", "急");
        map.put("Km", "救");

        map.put("Kn", "朽");
        map.put("Ko", "求");
        map.put("Kp", "汲");
        map.put("Kq", "泣");

        map.put("Kr", "灸");
        map.put("Ks", "球");
        map.put("Kt", "究");
        map.put("Ku", "窮");
        map.put("Kv", "笈");
        map.put("Kw", "級");
        map.put("Kx", "糾");
        map.put("Ky", "給");
        map.put("Kz", "旧");

        map.put("K.", "章");
        map.put("K,", "織");
        map.put("K!", "靭");
        map.put("K?", "政");
        map.put("K ", "雪");
        map.put("K'", "体");
        map.put("K:", "辰");
        map.put("K-", "致");
        map.put("KY", "嬬");


        // ---------------------- L
        map.put("La", "匡");
        map.put("Lb", "卿");
        map.put("Lc", "叫");
        map.put("Ld", "喬");
        map.put("Le", "境");
        map.put("Lf", "峡");
        map.put("Lg", "強");

        map.put("Lh", "彊");
        map.put("Li", "怯");
        map.put("Lj", "恐");
        map.put("Lk", "恭");
        map.put("Ll", "挟");
        map.put("Lm", "教");
        map.put("Ln", "橋");
        map.put("Lo", "況");
        map.put("Lp", "狂");
        map.put("Lq", "狭");
        map.put("Lr", "矯");
        map.put("Ls", "胸");
        map.put("Lt", "脅");
        map.put("Lu", "興");

        map.put("Lv", "蕎");
        map.put("Lw", "郷");
        map.put("Lx", "鏡");
        map.put("Ly", "響");
        map.put("Lz", "饗");

        map.put("L.", "笑");
        map.put("L,", "職");
        map.put("L!", "笥");
        map.put("L?", "整");
        map.put("L ", "絶");
        map.put("L'", "堆");
        map.put("L:", "奪");
        map.put("L-", "蜘");


        // ---------------------- M
        map.put("Ma", "菌");
        map.put("Mb", "衿");
        map.put("Mc", "襟");
        map.put("Md", "謹");
        map.put("Me", "近");
        map.put("Mf", "金");
        map.put("Mg", "吟");
        map.put("Mh", "銀");
        map.put("Mi", "九");
        map.put("Mj", "倶");
        map.put("Mk", "句");

        map.put("Ml", "区");
        map.put("Mm", "狗");
        map.put("Mn", "玖");
        map.put("Mo", "矩");
        map.put("Mp", "苦");
        map.put("Mq", "躯");
        map.put("Mr", "駆");
        map.put("Ms", "駈");
        map.put("Mt", "駒");
        map.put("Mu", "具");
        map.put("Mv", "愚");
        map.put("Mw", "虞");
        map.put("Mx", "喰");
        map.put("My", "空");

        map.put("Mz", "偶");

        map.put("M.", "粧");
        map.put("M,", "色");
        map.put("M!", "諏");
        map.put("M?", "星");
        map.put("M ", "舌");
        map.put("MP", "鎗");
        map.put("M'", "対");
        map.put("M:", "脱");
        map.put("M-", "遅");


        // ---------------------- N
        map.put("Na", "軍");
        map.put("Nb", "郡");
        map.put("Nc", "卦");
        map.put("Nd", "袈");
        map.put("Ne", "祁");
        map.put("Nf", "係");
        map.put("Ng", "傾");
        map.put("Nh", "刑");
        map.put("Ni", "兄");
        map.put("Nj", "啓");
        map.put("Nk", "圭");
        map.put("Nl", "珪");

        map.put("Nm", "型");
        map.put("Nn", "契");
        map.put("No", "形");
        map.put("Np", "径");
        map.put("Nq", "恵");
        map.put("Nr", "慶");
        map.put("Ns", "慧");
        map.put("Nt", "憩");
        map.put("Nu", "掲");
        map.put("Nv", "携");
        map.put("Nw", "敬");
        map.put("Nx", "景");
        map.put("Ny", "桂");
        map.put("Nz", "渓");

        map.put("N.", "紹");
        map.put("N,", "触");
        map.put("N!", "須");
        map.put("N?", "晴");
        map.put("N ", "蝉");
        map.put("NE", "像");
        map.put("N'", "耐");
        map.put("N:", "巽");
        map.put("N-", "馳");


        // ---------------------- O
        map.put("Oa", "欠");

        map.put("Ob", "決");
        map.put("Oc", "潔");
        map.put("Od", "穴");
        map.put("Oe", "結");
        map.put("Of", "血");
        map.put("Og", "訣");
        map.put("Oh", "月");
        map.put("Oi", "件");
        map.put("Oj", "倹");
        map.put("Ok", "倦");
        map.put("Ol", "健");
        map.put("Om", "兼");
        map.put("On", "券");
        map.put("Oo", "剣");

        map.put("Op", "喧");
        map.put("Oq", "圏");
        map.put("Or", "堅");
        map.put("Os", "嫌");
        map.put("Ot", "建");
        map.put("Ou", "憲");
        map.put("Ov", "懸");
        map.put("Ow", "拳");
        map.put("Ox", "捲");
        map.put("Oy", "検");
        map.put("Oz", "権");

        map.put("O.", "肖");
        map.put("O,", "食");
        map.put("O!", "酢");
        map.put("O?", "棲");
        map.put("O ", "仙");
        map.put("O'", "岱");
        map.put("O:", "竪");
        map.put("O-", "築");
        map.put("OK", "壷");


        // ---------------------- P
        map.put("Pa", "現");
        map.put("Pb", "絃");
        map.put("Pc", "舷");
        map.put("Pd", "言");
        map.put("Pe", "諺");

        map.put("Pf", "限");
        map.put("Pg", "乎");
        map.put("Ph", "個");
        map.put("Pi", "古");
        map.put("Pj", "呼");
        map.put("Pk", "固");
        map.put("Pl", "姑");
        map.put("Pm", "孤");
        map.put("Pn", "己");
        map.put("Po", "庫");
        map.put("Pp", "弧");
        map.put("Pq", "戸");
        map.put("Pr", "故");
        map.put("Ps", "枯");

        map.put("Pt", "湖");
        map.put("Pu", "狐");
        map.put("Pv", "糊");
        map.put("Pw", "袴");
        map.put("Px", "股");
        map.put("Py", "胡");
        map.put("Pz", "菰");

        map.put("P.", "菖");
        map.put("P,", "蝕");
        map.put("P!", "図");
        map.put("P?", "栖");
        map.put("P ", "先");
        map.put("PS", "臓");
        map.put("P'", "帯");
        map.put("P:", "辿");
        map.put("P-", "畜");
        map.put("PI", "釣");


        // ---------------------- Q
        map.put("Qa", "鯉");
        map.put("Qb", "交");
        map.put("Qc", "佼");
        map.put("Qd", "侯");
        map.put("Qe", "候");
        map.put("Qf", "倖");
        map.put("Qg", "光");
        map.put("Qh", "公");
        map.put("Qi", "功");

        map.put("Qj", "効");
        map.put("Qk", "勾");
        map.put("Ql", "厚");
        map.put("Qm", "口");
        map.put("Qn", "向");

        map.put("Qo", "后");
        map.put("Qp", "喉");
        map.put("Qq", "坑");
        map.put("Qr", "垢");
        map.put("Qs", "好");
        map.put("Qt", "孔");

        map.put("Qu", "孝");
        map.put("Qv", "宏");
        map.put("Qw", "工");
        map.put("Qx", "巧");
        map.put("Qy", "巷");
        map.put("Qz", "幸");

        map.put("Q.", "蒋");
        map.put("Q,", "辱");
        map.put("Q!", "厨");
        map.put("Q?", "正");
        map.put("Q ", "千");
        map.put("Q'", "待");
        map.put("Q:", "棚");
        map.put("Q-", "竹");


        // ---------------------- R
        map.put("Ra", "糠");
        map.put("Rb", "紅");
        map.put("Rc", "紘");
        map.put("Rd", "絞");
        map.put("Re", "綱");
        map.put("Rf", "耕");
        map.put("Rg", "考");
        map.put("Rh", "肯");
        map.put("Ri", "肱");
        map.put("Rj", "腔");

        map.put("Rk", "膏");
        map.put("Rl", "航");
        map.put("Rm", "荒");
        map.put("Rn", "行");
        map.put("Ro", "衡");
        map.put("Rp", "講");
        map.put("Rq", "貢");
        map.put("Rr", "購");
        map.put("Rs", "郊");
        map.put("Rt", "酵");
        map.put("Ru", "鉱");
        map.put("Rv", "砿");
        map.put("Rw", "鋼");
        map.put("Rx", "閤");

        map.put("Ry", "降");

        map.put("Rz", "項");

        map.put("R.", "蕉");
        map.put("R,", "尻");
        map.put("R!", "逗");
        map.put("R?", "清");
        map.put("R ", "占");
        map.put("RV", "憎");
        map.put("RR", "贈");
        map.put("RA", "造");
        map.put("R'", "怠");
        map.put("R:", "谷");
        map.put("R-", "筑");


        // ---------------------- S
        map.put("Sa", "惚");
        map.put("Sb", "骨");
        map.put("Sc", "狛");
        map.put("Sd", "込");
        map.put("Se", "此");
        map.put("Sf", "頃");
        map.put("Sg", "今");
        map.put("Sh", "困");
        map.put("Si", "坤");
        map.put("Sj", "墾");
        map.put("Sk", "婚");
        map.put("Sl", "恨");
        map.put("Sm", "懇");

        map.put("Sn", "昏");
        map.put("So", "昆");
        map.put("Sp", "根");
        map.put("Sq", "梱");
        map.put("Sr", "混");
        map.put("Ss", "痕");
        map.put("St", "紺");
        map.put("Su", "艮");
        map.put("Sv", "魂");
        map.put("Sw", "些");
        map.put("Sx", "佐");
        map.put("Sy", "叉");
        map.put("Sz", "唆");

        map.put("S.", "衝");
        map.put("S,", "伸");
        map.put("S!", "吹");
        map.put("S?", "牲");
        map.put("S ", "宣");
        map.put("SP", "総");
        map.put("SE", "足");
        map.put("S'", "態");
        map.put("S:", "狸");
        map.put("S-", "蓄");


        // ---------------------- T
        map.put("Ta", "済");
        map.put("Tb", "災");
        map.put("Tc", "采");

        map.put("Td", "犀");
        map.put("Te", "砕");
        map.put("Tf", "砦");
        map.put("Tg", "祭");
        map.put("Th", "斎");
        map.put("Ti", "細");
        map.put("Tj", "菜");
        map.put("Tk", "裁");
        map.put("Tl", "載");
        map.put("Tm", "際");
        map.put("Tn", "剤");
        map.put("To", "在");
        map.put("Tp", "材");
        map.put("Tq", "罪");

        map.put("Tr", "財");
        map.put("Ts", "冴");
        map.put("Tt", "坂");
        map.put("Tu", "阪");
        map.put("Tv", "堺");
        map.put("Tw", "榊");
        map.put("Tx", "肴");
        map.put("Ty", "咲");
        map.put("Tz", "崎");

        map.put("T.", "裳");
        map.put("T,", "信");
        map.put("T!", "垂");
        map.put("T?", "生");
        map.put("T ", "専");
        map.put("TE", "蔵");
        map.put("TF", "側");
        map.put("T'", "戴");
        map.put("T:", "鱈");
        map.put("T-", "逐");
        map.put("TO", "坪");


        // ---------------------- U
        map.put("Ua", "薩");
        map.put("Ub", "雑");
        map.put("Uc", "皐");
        map.put("Ud", "鯖");

        map.put("Ue", "捌");
        map.put("Uf", "錆");
        map.put("Ug", "鮫");
        map.put("Uh", "皿");
        map.put("Ui", "晒");
        map.put("Uj", "三");
        map.put("Uk", "傘");
        map.put("Ul", "参");
        map.put("Um", "山");
        map.put("Un", "惨");
        map.put("Uo", "撒");
        map.put("Up", "散");
        map.put("Uq", "桟");
        map.put("Ur", "燦");

        map.put("Us", "珊");
        map.put("Ut", "産");
        map.put("Uu", "算");
        map.put("Uv", "纂");
        map.put("Uw", "蚕");
        map.put("Ux", "讃");
        map.put("Uy", "賛");
        map.put("Uz", "酸");

        map.put("U.", "訟");
        map.put("U,", "侵");
        map.put("U!", "帥");
        map.put("U?", "盛");
        map.put("U ", "尖");
        map.put("UF", "争");
        map.put("U'", "替");
        map.put("U:", "樽");
        map.put("U-", "秩");
        map.put("UP", "吊");


        // ---------------------- V
        map.put("Va", "斯");
        map.put("Vb", "施");
        map.put("Vc", "旨");
        map.put("Vd", "枝");
        map.put("Ve", "止");

        map.put("Vf", "死");
        map.put("Vg", "氏");

        map.put("Vh", "獅");
        map.put("Vi", "祉");
        map.put("Vj", "私");
        map.put("Vk", "糸");
        map.put("Vl", "紙");
        map.put("Vm", "紫");
        map.put("Vn", "肢");
        map.put("Vo", "脂");
        map.put("Vp", "至");
        map.put("Vq", "視");
        map.put("Vr", "詞");
        map.put("Vs", "詩");
        map.put("Vt", "試");
        map.put("Vu", "誌");

        map.put("Vv", "諮");
        map.put("Vw", "資");
        map.put("Vx", "賜");
        map.put("Vy", "雌");
        map.put("Vz", "飼");

        map.put("V.", "証");
        map.put("V,", "唇");
        map.put("V!", "推");
        map.put("V?", "精");
        map.put("V ", "川");
        map.put("VA", "槍");
        map.put("V'", "泰");
        map.put("V:", "誰");
        map.put("V-", "窒");


        // ---------------------- W
        map.put("Wa", "識");
        map.put("Wb", "鴫");
        map.put("Wc", "竺");
        map.put("Wd", "軸");
        map.put("We", "宍");
        map.put("Wf", "雫");
        map.put("Wg", "七");
        map.put("Wh", "叱");
        map.put("Wi", "執");
        map.put("Wj", "失");
        map.put("Wk", "嫉");

        map.put("Wl", "室");
        map.put("Wm", "悉");
        map.put("Wn", "湿");
        map.put("Wo", "漆");
        map.put("Wp", "疾");
        map.put("Wq", "質");
        map.put("Wr", "実");
        map.put("Ws", "蔀");
        map.put("Wt", "篠");
        map.put("Wu", "偲");
        map.put("Wv", "柴");
        map.put("Ww", "芝");
        map.put("Wx", "屡");
        map.put("Wy", "蕊");

        map.put("Wz", "縞");

        map.put("W.", "詔");
        map.put("W,", "娠");
        map.put("W!", "水");
        map.put("W?", "聖");
        map.put("W ", "戦");
        map.put("WA", "葬");
        map.put("W'", "滞");
        map.put("W:", "丹");
        map.put("W-", "茶");


        // ---------------------- X
        map.put("Xa", "弱");

        map.put("Xb", "惹");
        map.put("Xc", "主");
        map.put("Xd", "取");
        map.put("Xe", "守");
        map.put("Xf", "手");
        map.put("Xg", "朱");
        map.put("Xh", "殊");
        map.put("Xi", "狩");
        map.put("Xj", "珠");
        map.put("Xk", "種");
        map.put("Xl", "腫");
        map.put("Xm", "趣");
        map.put("Xn", "酒");
        map.put("Xo", "首");

        map.put("Xp", "儒");
        map.put("Xq", "受");
        map.put("Xr", "呪");
        map.put("Xs", "寿");
        map.put("Xt", "授");
        map.put("Xu", "樹");
        map.put("Xv", "綬");
        map.put("Xw", "需");
        map.put("Xx", "囚");
        map.put("Xy", "収");
        map.put("Xz", "周");

        map.put("X.", "詳");
        map.put("X,", "寝");
        map.put("X!", "炊");
        map.put("X?", "声");
        map.put("X ", "扇");
        map.put("X'", "胎");
        map.put("X:", "単");
        map.put("X-", "嫡");


        // ---------------------- Y
        map.put("Ya", "住");
        map.put("Yb", "充");

        map.put("Yc", "十");
        map.put("Yd", "従");
        map.put("Ye", "戎");
        map.put("Yf", "柔");
        map.put("Yg", "汁");
        map.put("Yh", "渋");
        map.put("Yi", "獣");
        map.put("Yj", "縦");
        map.put("Yk", "重");
        map.put("Yl", "銃");
        map.put("Ym", "叔");
        map.put("Yn", "夙");
        map.put("Yo", "宿");
        map.put("Yp", "淑");

        map.put("Yq", "祝");
        map.put("Yr", "縮");
        map.put("Ys", "粛");
        map.put("Yt", "塾");
        map.put("Yu", "熟");
        map.put("Yv", "出");
        map.put("Yw", "術");
        map.put("Yx", "述");
        map.put("Yy", "俊");
        map.put("Yz", "峻");

        map.put("Y.", "象");
        map.put("Y,", "審");
        map.put("Y!", "睡");
        map.put("Y?", "製");
        map.put("Y ", "撰");
        map.put("Y'", "腿");
        map.put("Y:", "嘆");
        map.put("Y-", "着");
        map.put("YO", "紬");


        // ---------------------- Z
        map.put("Za", "緒");
        map.put("Zb", "署");
        map.put("Zc", "書");
        map.put("Zd", "薯");
        map.put("Ze", "藷");

        map.put("Zf", "諸");
        map.put("Zg", "助");
        map.put("Zh", "叙");
        map.put("Zi", "女");
        map.put("Zj", "序");
        map.put("Zk", "徐");
        map.put("Zl", "恕");
        map.put("Zm", "鋤");
        map.put("Zn", "除");
        map.put("Zo", "傷");
        map.put("Zp", "償");
        map.put("Zq", "勝");
        map.put("Zr", "匠");
        map.put("Zs", "升");

        map.put("Zt", "召");
        map.put("Zu", "哨");
        map.put("Zv", "商");
        map.put("Zw", "唱");
        map.put("Zx", "嘗");
        map.put("Zy", "奨");
        map.put("Zz", "妾");

        map.put("Z.", "賞");
        map.put("Z,", "心");
        map.put("Z!", "粋");
        map.put("Z?", "西");
        map.put("Z ", "栓");
        map.put("ZZ", "燥");
        map.put("Z'", "苔");
        map.put("Z:", "坦");
        map.put("Z-", "中");


        // ---------------------- a
        map.put("aa", "飴");
        map.put("ab", "絢");
        map.put("ac", "綾");
        map.put("ad", "鮎");
        map.put("ae", "或");

        map.put("af", "粟");
        map.put("ag", "袷");
        map.put("ah", "安");
        map.put("ai", "庵");
        map.put("aj", "按");
        map.put("ak", "暗");
        map.put("al", "案");
        map.put("am", "闇");
        map.put("an", "鞍");
        map.put("ao", "杏");
        map.put("ap", "以");
        map.put("aq", "伊");
        map.put("ar", "位");
        map.put("as", "依");

        map.put("at", "偉");
        map.put("au", "囲");
        map.put("av", "夷");
        map.put("aw", "委");
        map.put("ax", "威");
        map.put("ay", "尉");
        map.put("az", "惟");

        map.put("a.", "醤");
        map.put("a,", "慎");
        map.put("a!", "翠");
        map.put("a?", "誠");
        map.put("a ", "栴");
        map.put("a'", "袋");
        map.put("a:", "担");
        map.put("a-", "仲");


        // ---------------------- b
        map.put("ba", "逸");
        map.put("bb", "稲");
        map.put("bc", "茨");
        map.put("bd", "芋");
        map.put("be", "鰯");
        map.put("bf", "允");
        map.put("bg", "印");
        map.put("bh", "咽");
        map.put("bi", "員");

        map.put("bj", "因");
        map.put("bk", "姻");
        map.put("bl", "引");
        map.put("bm", "飲");
        map.put("bn", "淫");
        map.put("bo", "胤");
        map.put("bp", "蔭");
        map.put("bq", "院");
        map.put("br", "陰");
        map.put("bs", "隠");
        map.put("bt", "韻");

        map.put("bu", "吋");
        map.put("bv", "右");
        map.put("bw", "宇");
        map.put("bx", "烏");
        map.put("by", "羽");
        map.put("bz", "迂");

        map.put("b.", "鉦");
        map.put("b,", "振");
        map.put("b!", "衰");
        map.put("b?", "誓");
        map.put("b ", "泉");
        map.put("b'", "貸");
        map.put("b:", "探");
        map.put("b-", "宙");


        // ---------------------- c
        map.put("ca", "嬰");
        map.put("cb", "影");
        map.put("cc", "映");
        map.put("cd", "曳");
        map.put("ce", "栄");
        map.put("cf", "永");
        map.put("cg", "泳");
        map.put("ch", "洩");
        map.put("ci", "瑛");
        map.put("cj", "盈");

        map.put("ck", "穎");
        map.put("cl", "頴");
        map.put("cm", "英");
        map.put("cn", "衛");
        map.put("co", "詠");
        map.put("cp", "鋭");
        map.put("cq", "液");
        map.put("cr", "疫");
        map.put("cs", "益");
        map.put("ct", "駅");
        map.put("cu", "悦");
        map.put("cv", "謁");
        map.put("cw", "越");
        map.put("cx", "閲");

        map.put("cy", "榎");
        map.put("cz", "厭");

        map.put("c.", "鍾");
        map.put("c,", "新");
        map.put("c!", "遂");
        map.put("c?", "請");
        map.put("c ", "浅");
        map.put("c'", "退");
        map.put("c:", "旦");
        map.put("c-", "忠");


        // ---------------------- d
        map.put("da", "甥");
        map.put("db", "凹");
        map.put("dc", "央");
        map.put("dd", "奥");
        map.put("de", "往");
        map.put("df", "応");
        map.put("dg", "押");
        map.put("dh", "旺");
        map.put("di", "横");
        map.put("dj", "欧");
        map.put("dk", "殴");
        map.put("dl", "王");
        map.put("dm", "翁");

        map.put("dn", "襖");
        map.put("do", "鴬");
        map.put("dp", "鴎");
        map.put("dq", "黄");
        map.put("dr", "岡");
        map.put("ds", "沖");
        map.put("dt", "荻");
        map.put("du", "億");
        map.put("dv", "屋");
        map.put("dw", "憶");
        map.put("dx", "臆");
        map.put("dy", "桶");
        map.put("dz", "牡");

        map.put("d.", "鐘");
        map.put("d,", "晋");
        map.put("d!", "酔");
        map.put("d?", "逝");
        map.put("d ", "洗");
        map.put("d'", "逮");
        map.put("d:", "歎");
        map.put("d-", "抽");


        // ---------------------- e
        map.put("ea", "河");
        map.put("eb", "火");
        map.put("ec", "珂");

        map.put("ed", "禍");
        map.put("ee", "禾");
        map.put("ef", "稼");
        map.put("eg", "箇");
        map.put("eh", "花");
        map.put("ei", "苛");
        map.put("ej", "茄");
        map.put("ek", "荷");
        map.put("el", "華");
        map.put("em", "菓");
        map.put("en", "蝦");
        map.put("eo", "課");
        map.put("ep", "嘩");
        map.put("eq", "貨");

        map.put("er", "迦");
        map.put("es", "過");
        map.put("et", "霞");
        map.put("eu", "蚊");
        map.put("ev", "俄");
        map.put("ew", "峨");
        map.put("ex", "我");
        map.put("ey", "牙");
        map.put("ez", "画");

        map.put("e.", "障");
        map.put("e,", "森");
        map.put("e!", "錐");
        map.put("e?", "醒");
        map.put("e ", "染");
        map.put("e'", "隊");
        map.put("e:", "淡");
        map.put("e-", "昼");


        // ---------------------- f
        map.put("fa", "灰");
        map.put("fb", "界");
        map.put("fc", "皆");
        map.put("fd", "絵");

        map.put("fe", "芥");
        map.put("ff", "蟹");
        map.put("fg", "開");
        map.put("fh", "階");
        map.put("fi", "貝");
        map.put("fj", "凱");
        map.put("fk", "劾");
        map.put("fl", "外");
        map.put("fm", "咳");
        map.put("fn", "害");
        map.put("fo", "崖");
        map.put("fp", "慨");
        map.put("fq", "概");
        map.put("fr", "涯");

        map.put("fs", "碍");
        map.put("ft", "蓋");
        map.put("fu", "街");
        map.put("fv", "該");
        map.put("fw", "鎧");
        map.put("fx", "骸");
        map.put("fy", "浬");
        map.put("fz", "馨");

        map.put("f.", "鞘");
        map.put("f,", "榛");
        map.put("f!", "錘");
        map.put("f?", "青");
        map.put("f ", "潜");
        map.put("f'", "黛");
        map.put("f:", "湛");
        map.put("f-", "柱");


        // ---------------------- g
        map.put("ga", "岳");
        map.put("gb", "楽");
        map.put("gc", "額");
        map.put("gd", "顎");
        map.put("ge", "掛");
        map.put("gf", "笠");
        map.put("gg", "樫");

        map.put("gh", "橿");
        map.put("gi", "梶");
        map.put("gj", "鰍");
        map.put("gk", "潟");
        map.put("gl", "割");
        map.put("gm", "喝");
        map.put("gn", "恰");
        map.put("go", "括");
        map.put("gp", "活");
        map.put("gq", "渇");
        map.put("gr", "滑");
        map.put("gs", "葛");
        map.put("gt", "褐");
        map.put("gu", "轄");

        map.put("gv", "且");
        map.put("gw", "鰹");
        map.put("gx", "叶");
        map.put("gy", "椛");
        map.put("gz", "樺");

        map.put("g.", "上");
        map.put("g,", "浸");
        map.put("g!", "随");
        map.put("g?", "静");
        map.put("g ", "煎");
        map.put("g'", "鯛");
        map.put("g:", "炭");
        map.put("g-", "注");


        // ---------------------- h
        map.put("ha", "姦");
        map.put("hb", "完");
        map.put("hc", "官");
        map.put("hd", "寛");
        map.put("he", "干");
        map.put("hf", "幹");
        map.put("hg", "患");
        map.put("hh", "感");
        map.put("hi", "慣");
        map.put("hj", "憾");
        map.put("hk", "換");

        map.put("hl", "敢");
        map.put("hm", "柑");
        map.put("hn", "桓");
        map.put("ho", "棺");
        map.put("hp", "款");
        map.put("hq", "歓");
        map.put("hr", "汗");
        map.put("hs", "漢");
        map.put("ht", "澗");
        map.put("hu", "潅");
        map.put("hv", "環");
        map.put("hw", "甘");
        map.put("hx", "監");
        map.put("hy", "看");

        map.put("hz", "竿");

        map.put("h.", "丈");
        map.put("h,", "深");
        map.put("h!", "瑞");
        map.put("h?", "斉");
        map.put("h ", "煽");
        map.put("hX", "漕");
        map.put("h'", "代");
        map.put("h:", "短");
        map.put("h-", "虫");


        // ---------------------- i
        map.put("ia", "眼");

        map.put("ib", "岩");
        map.put("ic", "翫");
        map.put("id", "贋");
        map.put("ie", "雁");
        map.put("if", "頑");
        map.put("ig", "顔");
        map.put("ih", "願");
        map.put("ii", "企");
        map.put("ij", "伎");
        map.put("ik", "危");
        map.put("il", "喜");
        map.put("im", "器");
        map.put("in", "基");
        map.put("io", "奇");

        map.put("ip", "嬉");
        map.put("iq", "寄");
        map.put("ir", "岐");
        map.put("is", "希");
        map.put("it", "幾");
        map.put("iu", "忌");
        map.put("iv", "揮");
        map.put("iw", "机");
        map.put("ix", "旗");
        map.put("iy", "既");
        map.put("iz", "期");

        map.put("i.", "丞");
        map.put("i,", "申");
        map.put("i!", "髄");
        map.put("i?", "税");
        map.put("i ", "旋");
        map.put("i-", "属");
        map.put("i'", "台");
        map.put("i:", "端");


        // ---------------------- j
        map.put("ja", "宜");
        map.put("jb", "戯");

        map.put("jc", "技");
        map.put("jd", "擬");
        map.put("je", "欺");
        map.put("jf", "犠");
        map.put("jg", "疑");
        map.put("jh", "祇");
        map.put("ji", "義");
        map.put("jj", "蟻");
        map.put("jk", "誼");
        map.put("jl", "議");
        map.put("jm", "掬");
        map.put("jn", "菊");
        map.put("jo", "鞠");
        map.put("jp", "吉");

        map.put("jq", "吃");
        map.put("jr", "喫");
        map.put("js", "桔");
        map.put("jt", "橘");
        map.put("ju", "詰");
        map.put("jv", "砧");
        map.put("jw", "杵");
        map.put("jx", "黍");
        map.put("jy", "却");
        map.put("jz", "客");

        map.put("j.", "乗");
        map.put("j,", "疹");
        map.put("j!", "崇");
        map.put("j?", "脆");
        map.put("j ", "穿");
        map.put("j'", "大");
        map.put("j:", "箪");
        map.put("j-", "衷");


        // ---------------------- k
        map.put("ka", "牛");
        map.put("kb", "去");
        map.put("kc", "居");
        map.put("kd", "巨");
        map.put("ke", "拒");

        map.put("kf", "拠");
        map.put("kg", "挙");
        map.put("kh", "渠");
        map.put("ki", "虚");
        map.put("kj", "許");
        map.put("kk", "距");
        map.put("kl", "鋸");
        map.put("km", "漁");
        map.put("kn", "禦");
        map.put("ko", "魚");
        map.put("kp", "亨");
        map.put("kq", "享");
        map.put("kr", "京");
        map.put("ks", "供");

        map.put("kt", "侠");
        map.put("ku", "僑");
        map.put("kv", "兇");
        map.put("kw", "競");
        map.put("kx", "共");
        map.put("ky", "凶");
        map.put("kz", "協");

        map.put("k.", "冗");
        map.put("k,", "真");
        map.put("k!", "嵩");
        map.put("k?", "隻");
        map.put("k ", "箭");
        map.put("k)", "走");
        map.put("k-", "霜");
        map.put("k'", "第");
        map.put("k:", "綻");


        // ---------------------- l
        map.put("la", "驚");
        map.put("lb", "仰");
        map.put("lc", "凝");
        map.put("ld", "尭");
        map.put("le", "暁");
        map.put("lf", "業");
        map.put("lg", "局");
        map.put("lh", "曲");
        map.put("li", "極");

        map.put("lj", "玉");
        map.put("lk", "桐");
        map.put("ll", "粁");
        map.put("lm", "僅");
        map.put("ln", "勤");
        map.put("lo", "均");
        map.put("lp", "巾");
        map.put("lq", "錦");
        map.put("lr", "斤");
        map.put("ls", "欣");
        map.put("lt", "欽");
        map.put("lu", "琴");
        map.put("lv", "禁");
        map.put("lw", "禽");

        map.put("lx", "筋");
        map.put("ly", "緊");
        map.put("lz", "芹");

        map.put("l.", "剰");
        map.put("l,", "神");
        map.put("l!", "数");
        map.put("l?", "席");
        map.put("l ", "線");
        map.put("l'", "醍");
        map.put("l:", "耽");
        map.put("l-", "註");


        // ---------------------- m
        map.put("ma", "寓");
        map.put("mb", "遇");
        map.put("mc", "隅");
        map.put("md", "串");
        map.put("me", "櫛");
        map.put("mf", "釧");
        map.put("mg", "屑");
        map.put("mh", "屈");

        map.put("mi", "掘");
        map.put("mj", "窟");

        map.put("mk", "沓");
        map.put("ml", "靴");
        map.put("mm", "轡");
        map.put("mn", "窪");
        map.put("mo", "熊");
        map.put("mp", "隈");
        map.put("mq", "粂");
        map.put("mr", "栗");
        map.put("ms", "繰");
        map.put("mt", "桑");
        map.put("mu", "鍬");
        map.put("mv", "勲");
        map.put("mw", "君");
        map.put("mx", "薫");

        map.put("my", "訓");
        map.put("mz", "群");

        map.put("m.", "城");
        map.put("m,", "秦");
        map.put("m!", "枢");
        map.put("m?", "惜");
        map.put("m ", "繊");
        map.put("m'", "題");
        map.put("m:", "胆");
        map.put("m-", "酎");


        // ---------------------- n
        map.put("na", "畦");
        map.put("nb", "稽");
        map.put("nc", "系");
        map.put("nd", "経");
        map.put("ne", "継");
        map.put("nf", "繋");
        map.put("ng", "罫");
        map.put("nh", "茎");
        map.put("ni", "荊");
        map.put("nj", "蛍");
        map.put("nk", "計");
        map.put("nl", "詣");
        map.put("nm", "警");
        map.put("nn", "軽");

        map.put("no", "頚");
        map.put("np", "鶏");
        map.put("nq", "芸");
        map.put("nr", "迎");
        map.put("ns", "鯨");

        map.put("nt", "劇");
        map.put("nu", "戟");
        map.put("nv", "撃");
        map.put("nw", "激");
        map.put("nx", "隙");
        map.put("ny", "桁");
        map.put("nz", "傑");

        map.put("n.", "場");
        map.put("n,", "紳");
        map.put("n!", "趨");
        map.put("n?", "戚");
        map.put("n ", "羨");
        map.put("n'", "鷹");
        map.put("n:", "蛋");
        map.put("n-", "鋳");


        // ---------------------- o
        map.put("oa", "牽");
        map.put("ob", "犬");
        map.put("oc", "献");

        map.put("od", "研");
        map.put("oe", "硯");
        map.put("of", "絹");
        map.put("og", "県");
        map.put("oh", "肩");
        map.put("oi", "見");
        map.put("oj", "謙");
        map.put("ok", "賢");
        map.put("ol", "軒");
        map.put("om", "遣");
        map.put("on", "鍵");
        map.put("oo", "険");
        map.put("op", "顕");
        map.put("oq", "験");

        map.put("or", "鹸");
        map.put("os", "元");
        map.put("ot", "原");
        map.put("ou", "厳");
        map.put("ov", "幻");
        map.put("ow", "弦");
        map.put("ox", "減");
        map.put("oy", "源");
        map.put("oz", "玄");

        map.put("o.", "壌");
        map.put("o,", "臣");
        map.put("o!", "雛");
        map.put("o?", "斥");
        map.put("o ", "腺");
        map.put("o'", "滝");
        map.put("o:", "誕");
        map.put("o-", "駐");


        // ---------------------- p
        map.put("pa", "虎");
        map.put("pb", "誇");
        map.put("pc", "跨");
        map.put("pd", "鈷");
        map.put("pe", "雇");
        map.put("pf", "顧");
        map.put("pg", "鼓");

        map.put("ph", "五");
        map.put("pi", "互");
        map.put("pj", "伍");
        map.put("pk", "午");
        map.put("pl", "呉");
        map.put("pm", "吾");
        map.put("pn", "娯");
        map.put("po", "後");
        map.put("pp", "御");
        map.put("pq", "悟");
        map.put("pr", "梧");
        map.put("ps", "檎");
        map.put("pt", "瑚");
        map.put("pu", "碁");

        map.put("pv", "語");
        map.put("pw", "誤");
        map.put("px", "護");
        map.put("py", "醐");
        map.put("pz", "乞");

        map.put("p.", "嬢");
        map.put("p,", "芯");
        map.put("p!", "据");
        map.put("p?", "昔");
        map.put("p ", "舛");
        map.put("p'", "瀧");
        map.put("p:", "鍛");
        map.put("p-", "樗");


        // ---------------------- q
        map.put("qa", "広");
        map.put("qb", "庚");
        map.put("qc", "康");
        map.put("qd", "弘");
        map.put("qe", "恒");
        map.put("qf", "慌");
        map.put("qg", "抗");
        map.put("qh", "拘");

        map.put("qi", "控");
        map.put("qj", "攻");
        map.put("qk", "昂");
        map.put("ql", "晃");
        map.put("qm", "更");
        map.put("qn", "杭");
        map.put("qo", "校");
        map.put("qp", "梗");
        map.put("qq", "構");
        map.put("qr", "江");
        map.put("qs", "洪");
        map.put("qt", "浩");
        map.put("qu", "港");
        map.put("qv", "溝");

        map.put("qw", "甲");
        map.put("qx", "皇");
        map.put("qy", "硬");
        map.put("qz", "稿");

        map.put("q.", "常");
        map.put("q,", "薪");
        map.put("q!", "杉");
        map.put("q?", "析");
        map.put("q ", "船");
        map.put("q'", "卓");
        map.put("q:", "団");
        map.put("q-", "瀦");


        // ---------------------- r
        map.put("ra", "香");
        map.put("rb", "高");
        map.put("rc", "鴻");
        map.put("rd", "剛");
        map.put("re", "劫");
        map.put("rf", "号");
        map.put("rg", "合");
        map.put("rh", "壕");
        map.put("ri", "拷");
        map.put("rj", "濠");
        map.put("rk", "豪");

        map.put("rl", "轟");
        map.put("rm", "麹");
        map.put("rn", "克");
        map.put("ro", "刻");
        map.put("rp", "告");
        map.put("rq", "国");
        map.put("rr", "穀");
        map.put("rs", "酷");
        map.put("rt", "鵠");
        map.put("ru", "黒");
        map.put("rv", "獄");
        map.put("rw", "漉");
        map.put("rx", "腰");
        map.put("ry", "甑");

        map.put("rz", "忽");

        map.put("r.", "情");
        map.put("r,", "親");
        map.put("r!", "椙");
        map.put("r?", "石");
        map.put("r ", "薦");
        map.put("r'", "啄");
        map.put("r:", "壇");
        map.put("r-", "猪");


        // ---------------------- s
        map.put("sa", "嵯");

        map.put("sb", "左");
        map.put("sc", "差");
        map.put("sd", "査");
        map.put("se", "沙");
        map.put("sf", "瑳");
        map.put("sg", "砂");
        map.put("sh", "詐");
        map.put("si", "鎖");
        map.put("sj", "裟");
        map.put("sk", "坐");
        map.put("sl", "座");
        map.put("sm", "挫");
        map.put("sn", "債");
        map.put("so", "催");

        map.put("sp", "再");
        map.put("sq", "最");
        map.put("sr", "哉");
        map.put("ss", "塞");
        map.put("st", "妻");
        map.put("su", "宰");
        map.put("sv", "彩");
        map.put("sw", "才");
        map.put("sx", "採");
        map.put("sy", "栽");
        map.put("sz", "歳");

        map.put("s.", "擾");
        map.put("s,", "診");
        map.put("s!", "菅");
        map.put("s?", "積");
        map.put("s ", "詮");
        map.put("s'", "宅");
        map.put("s:", "弾");
        map.put("s-", "苧");


        // ---------------------- t
        map.put("ta", "埼");
        map.put("tb", "碕");
        map.put("tc", "鷺");
        map.put("td", "作");
        map.put("te", "削");

        map.put("tf", "咋");
        map.put("tg", "搾");
        map.put("th", "昨");
        map.put("ti", "朔");
        map.put("tj", "柵");
        map.put("tk", "窄");
        map.put("tl", "策");
        map.put("tm", "索");
        map.put("tn", "錯");
        map.put("to", "桜");
        map.put("tp", "鮭");
        map.put("tq", "笹");
        map.put("tr", "匙");
        map.put("ts", "冊");
        map.put("tt", "刷");

        map.put("tu", "察");
        map.put("tv", "拶");
        map.put("tw", "撮");
        map.put("tx", "擦");
        map.put("ty", "札");
        map.put("tz", "殺");

        map.put("t.", "条");
        map.put("t,", "身");
        map.put("t!", "頗");
        map.put("t?", "籍");
        map.put("t ", "賎");
        map.put("t'", "托");
        map.put("t:", "断");
        map.put("t-", "著");


        // ---------------------- u
        map.put("ua", "餐");
        map.put("ub", "斬");
        map.put("uc", "暫");
        map.put("ud", "残");
        map.put("ue", "仕");
        map.put("uf", "仔");

        map.put("ug", "伺");
        map.put("uh", "使");
        map.put("ui", "刺");
        map.put("uj", "司");
        map.put("uk", "史");
        map.put("ul", "嗣");
        map.put("um", "四");
        map.put("un", "士");
        map.put("uo", "始");
        map.put("up", "姉");
        map.put("uq", "姿");
        map.put("ur", "子");
        map.put("us", "屍");
        map.put("ut", "市");

        map.put("uu", "師");
        map.put("uv", "志");
        map.put("uw", "思");
        map.put("ux", "指");
        map.put("uy", "支");
        map.put("uz", "孜");

        map.put("u.", "杖");
        map.put("u,", "辛");
        map.put("u!", "雀");
        map.put("u?", "績");
        map.put("u ", "践");
        map.put("u'", "択");
        map.put("u:", "暖");
        map.put("u-", "貯");


        // ---------------------- v
        map.put("va", "歯");
        map.put("vb", "事");
        map.put("vc", "似");
        map.put("vd", "侍");
        map.put("ve", "児");
        map.put("vf", "字");
        map.put("vg", "寺");
        map.put("vh", "慈");
        map.put("vi", "持");

        map.put("vj", "時");
        map.put("vk", "次");
        map.put("vl", "滋");
        map.put("vm", "治");
        map.put("vn", "爾");
        map.put("vo", "璽");
        map.put("vp", "痔");
        map.put("vq", "磁");
        map.put("vr", "示");
        map.put("vs", "而");
        map.put("vt", "耳");
        map.put("vu", "自");
        map.put("vv", "蒔");
        map.put("vw", "辞");

        map.put("vx", "汐");
        map.put("vy", "鹿");
        map.put("vz", "式");

        map.put("v.", "浄");
        map.put("v,", "進");
        map.put("v!", "裾");
        map.put("v?", "脊");
        map.put("v ", "選");
        map.put("v'", "拓");
        map.put("v:", "檀");
        map.put("v-", "丁");


        // ---------------------- w
        map.put("wa", "舎");
        map.put("wb", "写");
        map.put("wc", "射");
        map.put("wd", "捨");
        map.put("we", "赦");
        map.put("wf", "斜");
        map.put("wg", "煮");
        map.put("wh", "社");
        map.put("wi", "紗");
        map.put("wj", "者");
        map.put("wk", "謝");
        map.put("wl", "車");
        map.put("wm", "遮");

        map.put("wn", "蛇");
        map.put("wo", "邪");
        map.put("wp", "借");
        map.put("wq", "勺");
        map.put("wr", "尺");
        map.put("ws", "杓");
        map.put("wt", "灼");
        map.put("wu", "爵");
        map.put("wv", "酌");
        map.put("ww", "釈");
        map.put("wx", "錫");
        map.put("wy", "若");
        map.put("wz", "寂");

        map.put("w.", "状");
        map.put("w,", "針");
        map.put("w!", "澄");
        map.put("w?", "責");
        map.put("w ", "遷");
        map.put("w'", "沢");
        map.put("w:", "段");
        map.put("w-", "兆");


        // ---------------------- x
        map.put("xa", "宗");
        map.put("xb", "就");
        map.put("xc", "州");
        map.put("xd", "修");
        map.put("xe", "愁");
        map.put("xf", "拾");
        map.put("xg", "洲");
        map.put("xh", "秀");
        map.put("xi", "秋");
        map.put("xj", "終");
        map.put("xk", "繍");
        map.put("xl", "習");
        map.put("xm", "臭");
        map.put("xn", "舟");

        map.put("xo", "蒐");
        map.put("xp", "衆");
        map.put("xq", "襲");
        map.put("xr", "讐");
        map.put("xs", "蹴");
        map.put("xt", "輯");
        map.put("xu", "週");
        map.put("xv", "酋");
        map.put("xw", "酬");
        map.put("xx", "集");
        map.put("xy", "醜");
        map.put("xz", "什");

        map.put("x.", "畳");
        map.put("x,", "震");
        map.put("x!", "摺");
        map.put("x?", "赤");
        map.put("x ", "銭");
        map.put("x'", "濯");
        map.put("x:", "男");
        map.put("x-", "凋");


        // ---------------------- y
        map.put("ya", "春");
        map.put("yb", "瞬");
        map.put("yc", "竣");
        map.put("yd", "舜");

        map.put("ye", "駿");
        map.put("yf", "准");
        map.put("yg", "循");
        map.put("yh", "旬");
        map.put("yi", "楯");
        map.put("yj", "殉");
        map.put("yk", "淳");

        map.put("yl", "準");
        map.put("ym", "潤");
        map.put("yn", "盾");
        map.put("yo", "純");
        map.put("yp", "巡");
        map.put("yq", "遵");

        map.put("yr", "醇");
        map.put("ys", "順");
        map.put("yt", "処");
        map.put("yu", "初");
        map.put("yv", "所");
        map.put("yw", "暑");
        map.put("yx", "曙");
        map.put("yy", "渚");
        map.put("yz", "庶");

        map.put("y.", "穣");
        map.put("y,", "人");
        map.put("y!", "寸");
        map.put("y?", "跡");
        map.put("y ", "銑");
        map.put("y'", "琢");
        map.put("y:", "談");
        map.put("y-", "喋");


        // ---------------------- z
        map.put("za", "娼");
        map.put("zb", "宵");
        map.put("zc", "将");
        map.put("zd", "小");
        map.put("ze", "少");
        map.put("zf", "尚");
        map.put("zg", "庄");

        map.put("zh", "床");
        map.put("zi", "廠");
        map.put("zj", "彰");
        map.put("zk", "承");
        map.put("zl", "抄");
        map.put("zm", "招");
        map.put("zn", "掌");
        map.put("zo", "捷");
        map.put("zp", "昇");
        map.put("zq", "昌");
        map.put("zr", "昭");
        map.put("zs", "晶");
        map.put("zt", "松");
        map.put("zu", "梢");

        map.put("zv", "樟");
        map.put("zw", "樵");
        map.put("zx", "沼");
        map.put("zy", "消");
        map.put("zz", "渉");

        map.put("z.", "蒸");
        map.put("z,", "仁");
        map.put("z!", "世");
        map.put("z?", "蹟");
        map.put("z ", "閃");
        map.put("z'", "託");
        map.put("z:", "値");
        map.put("z-", "寵");


        // ---------------------- space
        map.put(" A", "鮮");
        map.put(" B", "前");
        map.put(" C", "善");
        map.put(" D", "漸");
        map.put(" E", "然");
        map.put(" F", "全");
        map.put(" G", "禅");
        map.put(" H", "繕");

        map.put(" I", "膳");
        map.put(" J", "糎");
        map.put(" K", "噌");
        map.put(" L", "塑");
        map.put(" M", "岨");
        map.put(" N", "措");
        map.put(" O", "曾");
        map.put(" P", "曽");
        map.put(" Q", "楚");
        map.put(" R", "狙");
        map.put(" S", "疏");
        map.put(" T", "疎");
        map.put(" U", "礎");
        map.put(" V", "祖");

        map.put(" W", "租");
        map.put(" X", "粗");
        map.put(" Y", "素");
        map.put(" Z", "組");
        map.put(" a", "蘇");
        map.put(" b", "訴");
        map.put(" c", "阻");
        map.put(" d", "遡");
        map.put(" e", "鼠");
        map.put(" f", "僧");
        map.put(" g", "創");
        map.put(" h", "双");
        map.put(" i", "叢");
        map.put(" j", "倉");

        map.put(" k", "喪");
        map.put(" l", "壮");
        map.put(" m", "奏");
        map.put(" n", "爽");
        map.put(" o", "宋");
        map.put(" p", "層");
        map.put(" q", "匝");
        map.put(" r", "惣");
        map.put(" s", "想");
        map.put(" t", "捜");
        map.put(" u", "掃");
        map.put(" v", "挿");
        map.put(" w", "掻");

        map.put(" x", "操");
        map.put(" y", "早");
        map.put(" z", "曹");

        map.put(" (", "藻");

        // ---------------------- stutter
        map.put("-C", "綜");
        map.put("-F", "測");
        map.put("-R", "俗");
        map.put("-B", "賊");

        map.put("-A", "帳");
        map.put("-D", "庁");
        map.put("-E", "弔");
        map.put("-G", "張");
        map.put("-H", "彫");
        map.put("-I", "徴");
        map.put("-J", "懲");
        map.put("-K", "挑");
        map.put("-L", "暢");
        map.put("-M", "朝");
        map.put("-N", "潮");
        map.put("-O", "牒");
        map.put("-P", "町");
        map.put("-Q", "眺");

        map.put("-S", "聴");
        map.put("-T", "脹");
        map.put("-U", "腸");
        map.put("-V", "蝶");
        map.put("-W", "調");
        map.put("-X", "諜");
        map.put("-Y", "超");
        map.put("-Z", "跳");
        map.put("-a", "銚");
        map.put("-b", "長");
        map.put("-c", "頂");
        map.put("-d", "鳥");
        map.put("-e", "勅");
        map.put("-f", "捗");

        map.put("-g", "直");
        map.put("-h", "朕");
        map.put("-i", "沈");
        map.put("-j", "珍");
        map.put("-k", "賃");
        map.put("-l", "鎮");
        map.put("-m", "陳");
        map.put("-n", "津");
        map.put("-o", "墜");
        map.put("-p", "椎");
        map.put("-q", "槌");
        map.put("-r", "追");
        map.put("-s", "鎚");
        map.put("-t", "痛");

        map.put("-u", "通");
        map.put("-v", "塚");
        map.put("-w", "栂");
        map.put("-x", "掴");
        map.put("-y", "槻");
        map.put("-z", "佃");
        map.put("-0", "柘");

        map.put("-2", "騒");
        // ----------------------  end


        // ---------------------- punctuation
        map.put("(W", "装");

        map.put(") ", "送");

        map.put("'e", "捉");

        map.put("!!", "続");
        map.put("'m", "卒");
        map.put("'v", "袖");
        map.put("??", "其");
        map.put("'t", "揃");
        map.put("'s", "存");
        map.put("!?", "孫");

        map.put("?!", "尊");
        map.put(": ", "損");
        map.put("'r", "村");
        map.put("'d", "遜");
        map.put("'l", "他");
        map.put("..", "多");
        map.put(". ", "太");
        map.put("! ", "汰");
        map.put("? ", "詑");
        map.put("' ", "唾");
        map.put(", ", "惰");


        // ---------------------- number
        map.put("00", "辻");
        map.put("01", "蔦");
        map.put("02", "綴");
        map.put("03", "鍔");
        map.put("04", "椿");
        map.put("05", "潰");
        
        map.put("RM" , "亭");
        map.put("MU" , "低");
        map.put("UC" , "停");
        map.put("AD" , "偵");
        map.put("-1" , "剃");
        map.put("-3" , "貞");

        map.put("x1" , "呈");
        map.put("x2" , "堤");
        map.put("x3" , "定");
        map.put(" 1" , "帝");
        map.put(" 2" , "底");
        map.put(" 3" , "庭");
        map.put(" 4" , "廷");
        map.put(" 5" , "弟");
        map.put(" 6" , "悌");
        map.put(" 7" , "抵");
        map.put(" 8" , "挺");
        map.put(" 9" , "提");
        map.put(" 0" , "梯");
        map.put("1 " , "汀");

        map.put("2 " , "碇");
        map.put("3 " , "禎");
        map.put("4 " , "程");
        map.put("5 " , "締");
        map.put("6 " , "艇");
        map.put("7 " , "訂");
        map.put("8 " , "諦");
        map.put("9 " , "蹄");
        map.put("0 " , "逓");

        map.put(" &" , "邸");
        map.put("& " , "鄭");

        map.put("II" , "釘");
        map.put("IV" , "鼎");
        map.put("VI" , "泥");
        map.put("IX" , "摘");
        map.put("XI" , "擢");
        map.put("XX" , "敵");
        map.put("XL" , "滴");
        map.put("LX" , "的");
        map.put(" ~" , "笛");
        map.put("~ " , "適");
        map.put("% " , "鏑");
        map.put("1%" , "溺");
        map.put("2%" , "哲");
        map.put("3%" , "徹");

        //Page 45
        map.put("4%" , "撤");
        map.put("5%" , "轍");
        map.put("6%" , "迭");
        map.put("7%" , "鉄");
        map.put("8%" , "典");
        map.put("9%" , "填");
        map.put("0%" , "天");
        map.put(".1" , "展");
        map.put(".2" , "店");
        map.put(".3" , "添");
        map.put(".4" , "纏");
        map.put(".5" , "甜");
        map.put(".6" , "貼");
        map.put(".7" , "転");

        map.put(".8" , "顛");
        map.put(".9" , "点");
        map.put(".0" , "伝");
        map.put("1." , "殿");
        map.put("2." , "澱");
        map.put("3." , "田");
        map.put("4." , "電");
        map.put("5." , "兎");
        map.put("6." , "吐");
        map.put("7." , "堵");
        map.put("8." , "塗");
        map.put("9." , "妬");
        map.put("0." , "屠");
        map.put("1," , "徒");

        map.put("2," , "斗");
        map.put("3," , "杜");
        map.put("4," , "渡");
        map.put("5," , "登");
        map.put("6," , "菟");
        map.put("7," , "賭");
        map.put("8," , "途");
        map.put("9," , "都");
        map.put("0," , "鍍");
        map.put(",1" , "砥");
        map.put(",2" , "砺");
        map.put(",3" , "努");
        map.put(",4" , "度");
        map.put(",5" , "土");

        map.put(",6" , "奴");
        map.put(",7" , "怒");
        map.put(",8" , "倒");
        map.put(",9" , "党");
        map.put(",0" , "冬");

        map.put("XV" , "凍");
        map.put("06" , "刀");
        map.put("07" , "唐");
        map.put("08" , "塔");
        map.put("09" , "塘");
        map.put("10" , "套");
        map.put("11" , "宕");
        map.put("12" , "島");

        map.put("13" , "嶋");
        map.put("14" , "悼");
        map.put("15" , "投");
        map.put("16" , "搭");
        map.put("17" , "東");
        map.put("18" , "桃");
        map.put("19" , "梼");
        map.put("20" , "棟");
        map.put("21" , "盗");
        map.put("22" , "淘");
        map.put("23" , "湯");
        map.put("24" , "涛");
        map.put("25" , "灯");
        map.put("26" , "燈");

        map.put("27" , "当");
        map.put("28" , "痘");
        map.put("29" , "祷");
        map.put("30" , "等");
        map.put("31" , "答");
        map.put("32" , "筒");
        map.put("33" , "糖");
        map.put("34" , "統");
        map.put("35" , "到");
        map.put("36" , "董");
        map.put("37" , "蕩");
        map.put("38" , "藤");
        map.put("39" , "討");
        map.put("40" , "謄");

        map.put("41" , "豆");
        map.put("42" , "踏");
        map.put("43" , "逃");
        map.put("44" , "透");
        map.put("45" , "鐙");
        map.put("46" , "陶");
        map.put("47" , "頭");
        map.put("48" , "騰");
        map.put("49" , "闘");
        map.put("50" , "働");
        map.put("51" , "動");
        map.put("52" , "同");
        map.put("53" , "堂");
        map.put("54" , "導");

        map.put("55" , "憧");
        map.put("56" , "撞");
        map.put("57" , "洞");
        map.put("58" , "瞳");
        map.put("59" , "童");
        map.put("60" , "胴");
        map.put("61" , "萄");
        map.put("62" , "道");
        map.put("63" , "銅");
        map.put("64" , "峠");
        map.put("65" , "鴇");
        map.put("66" , "匿");
        map.put("67" , "得");
        map.put("68" , "徳");

        //Page 46
        map.put("69" , "涜");
        map.put("70" , "特");
        map.put("71" , "督");
        map.put("72" , "禿");
        map.put("73" , "篤");
        map.put("74" , "毒");
        map.put("75" , "独");
        map.put("76" , "読");
        map.put("77" , "栃");
        map.put("78" , "橡");
        map.put("79" , "凸");
        map.put("80" , "突");
        map.put("81" , "椴");
        map.put("82" , "届");

        map.put("83" , "鳶");
        map.put("84" , "苫");
        map.put("85" , "寅");
        map.put("86" , "酉");
        map.put("87" , "瀞");
        map.put("88" , "噸");
        map.put("89" , "屯");
        map.put("90" , "惇");
        map.put("91" , "敦");
        map.put("92" , "沌");
        map.put("93" , "豚");
        map.put("94" , "遁");
        map.put("95" , "頓");
        map.put("96" , "呑");

        map.put("97" , "曇");
        map.put("98" , "鈍");
        map.put("99" , "奈");
        map.put("(A" , "那");
        map.put("(B" , "内");
        map.put("(C" , "乍");
        map.put("(D" , "凪");
        map.put("(E" , "薙");
        map.put("(F" , "謎");
        map.put("(G" , "灘");
        map.put("(H" , "捺");
        map.put("(I" , "鍋");
        map.put("(J" , "楢");
        map.put("(K" , "馴");

        map.put("(L" , "縄");
        map.put("(M" , "畷");
        map.put("(N" , "南");
        map.put("(O" , "楠");
        map.put("(P" , "軟");
        map.put("(Q" , "難");
        map.put("(R" , "汝");
        map.put("(S" , "二");
        map.put("(T" , "尼");
        map.put("(U" , "弐");
        map.put("(V" , "迩");
        map.put("(X" , "匂");
        map.put("(Y" , "賑");
        map.put("(Z" , "肉");

        map.put("a)" , "虹");
        map.put("b)" , "廿");
        map.put("c)" , "日");
        map.put("d)" , "乳");
        map.put("e)" , "入");

        map.put("f)" , "如");
        map.put("g)" , "尿");
        map.put("h)" , "韮");
        map.put("i)" , "任");
        map.put("j)" , "妊");
        map.put("l)" , "忍");

        map.put("m)" , "認");
        map.put("n)" , "濡");
        map.put("o)" , "禰");
        map.put("p)" , "祢");
        map.put("q)" , "寧");
        map.put("r)" , "葱");
        map.put("s)" , "猫");
        map.put("t)" , "熱");
        map.put("u)" , "年");
        map.put("v)" , "念");
        map.put("w)" , "捻");
        map.put("x)" , "撚");
        map.put("y)" , "燃");
        map.put("z)" , "粘");

        map.put(".)" , "乃");
        map.put("!)" , "廼");
        map.put("?)" , "之");
        map.put("(1" , "埜");
        map.put("(2" , "嚢");
        map.put("(3" , "悩");
        map.put("(4" , "濃");
        map.put("(5" , "納");
        map.put("(6" , "能");
        map.put("(7" , "脳");
        map.put("(8" , "膿");
        map.put("(9" , "農");
        map.put("(0" , "覗");
        map.put("1)" , "蚤");

        map.put("2)" , "巴");
        map.put("3)" , "把");
        map.put("4)" , "播");
        map.put("5)" , "覇");
        map.put("6)" , "杷");
        map.put("7)" , "波");
        map.put("8)" , "派");
        map.put("9)" , "琶");
        map.put("0)" , "破");
        map.put("EB" , "婆");
                
        map.put("1R" , "罵");
        map.put("7R" , "芭");
        map.put("8B" , "馬");
        map.put("A1" , "俳");
        map.put("AG" , "廃");
        map.put("AI" , "拝");
        map.put("AK" , "排");
        map.put("AL" , "敗");
        map.put("AN" , "杯");
        map.put("AS" , "盃");
        map.put("AT" , "牌");
        map.put("BA" , "背");
        map.put("BE" , "肺");
        map.put("BG" , "輩");
        map.put("BS" , "配");
        map.put("BW" , "倍");
        map.put("CD" , "培");
        map.put("CI" , "媒");
        map.put("CK" , "梅");
        map.put("CO" , "楳");
        map.put("CT" , "煤");
        map.put("D)" , "狽");
        map.put("D1" , "買");
        map.put("D2" , "売");
        map.put("DD" , "賠");
        map.put("DE" , "陪");
        map.put("DJ" , "這");
        map.put("DO" , "蝿");
        map.put("DR" , "秤");
        map.put("DU" , "矧");
        map.put("DV" , "萩");
        map.put("DX" , "伯");
        map.put("EC" , "剥");
        map.put("ED" , "博");
        map.put("eG" , "拍");
        map.put("EL" , "柏");
        map.put("ES" , "泊");
        map.put("eX" , "白");
        map.put("EX" , "箔");
        map.put("FF" , "粕");
        map.put("FI" , "舶");
        map.put("FL" , "薄");
        map.put("FS" , "迫");
        map.put("GI" , "曝");
        map.put("GM" , "漠");
        map.put("GO" , "爆");
        map.put("GZ" , "縛");
        map.put("HE" , "莫");
        map.put("IC" , "駁");
        map.put("IE" , "麦");
        map.put("IF" , "函");
        map.put("IR" , "箱");
        map.put("IS" , "硲");
        map.put("iV" , "箸");
        map.put("KE" , "肇");
        map.put("L)" , "筈");
        map.put("L3" , "櫨");
        map.put("LD" , "幡");
        map.put("LE" , "肌");
        map.put("LI" , "畑");
        map.put("LL" , "畠");
        map.put("LO" , "八");
        map.put("LU" , "鉢");
        map.put("LY" , "溌");
        map.put("MA" , "発");
        map.put("MF" , "醗");
        map.put("MI" , "髪");
        map.put("MO" , "伐");
        map.put("MS" , "罰");
        map.put("MX" , "抜");
        map.put("NA" , "筏");
        map.put("NF" , "閥");
        map.put("NG" , "鳩");
        map.put("NP" , "噺");
        map.put("NR" , "塙");
        map.put("NT" , "蛤");
        map.put("OF" , "隼");
        map.put("OG" , "伴");
        map.put("OL" , "判");
        map.put("OM" , "半");
        map.put("ON" , "反");
        map.put("OO" , "叛");
        map.put("OP" , "帆");
        map.put("OR" , "搬");
        map.put("OU" , "斑");
        map.put("OW" , "板");
        map.put("PR" , "氾");
        map.put("R)" , "汎");
        map.put("R3" , "版");
        map.put("RB" , "犯");
        map.put("RE" , "班");
        map.put("RO" , "畔");
        map.put("RX" , "繁");
        map.put("RY" , "般");
        map.put("S)" , "藩");
        map.put("SA" , "販");
        map.put("SF" , "範");
        map.put("SI" , "釆");
        map.put("SK" , "煩");
        map.put("SO" , "頒");
        map.put("ST" , "飯");
        map.put("SV" , "挽");
        map.put("TA" , "晩");
        map.put("TD" , "番");
        map.put("TH" , "盤");
        map.put("TL" , "磐");
        map.put("TT" , "蕃");
        map.put("TV" , "蛮");
        map.put("UN" , "匪");
        map.put("UT" , "卑");
        map.put("VC" , "否");
        map.put("VE" , "妃");
        map.put("VT" , "庇");
        map.put("WN" , "彼");
        map.put("WS" , "悲");
        map.put("XP" , "扉");
        map.put("YT" , "批");
                
        map.put("HQ" , "披");
        map.put(" +" , "斐");
        map.put("+1" , "比");
        map.put("+2" , "泌");
        map.put("+3" , "疲");
        map.put("+4" , "皮");
        map.put("+5" , "碑");
        map.put("+6" , "秘");
        map.put("+7" , "緋");
        map.put("+8" , "罷");
        map.put("+9" , "肥");
        map.put("+ " , "被");
                
        map.put(".!" , "誹");
        map.put(".?" , "費");
        
        // ----------------------  end
    }
}
