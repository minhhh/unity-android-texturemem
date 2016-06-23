package com.android.utils.androidmem;

import android.app.*;
import android.os.*;
import com.unity3d.player.UnityPlayer;

import java.text.DecimalFormat;

public class AndroidMem {
    public static final long BytesInMB = 1024L * 1024L;
    public static final long BytesInKB = 1024L;

    public static String getMemoryDetailInfo() {
        ActivityManager am = (ActivityManager) UnityPlayer.currentActivity.getSystemService("activity");
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(memoryInfo);

        String availMem = byteToMB(memoryInfo.availMem);
        String threshold = byteToMB(memoryInfo.threshold);
        StringBuilder buff = new StringBuilder();
        buff.append(String.format("System  Mem : avail=%s, threshold=%s\n", new Object[]{availMem, threshold}));

        int[] pids = new int[1];
        pids[0] = android.os.Process.myPid();
        Debug.MemoryInfo[] dmi = am.getProcessMemoryInfo(pids);

        String totalPrivate = kbToMB(dmi[0].getTotalPrivateDirty());
        String totalPss = kbToMB(dmi[0].getTotalPss());
        String totalShared = kbToMB(dmi[0].getTotalSharedDirty());
        buff.append(String.format("Process Mem : TotalPss=%s, TotalPrivate=%s, TotalShared=%s\n", new Object[]{totalPss, totalPrivate, totalShared}));

        buff.append(getVMHeapInfo() + "\n");
        buff.append(getNativeHeapInfo() + "\n");

        return buff.toString();
    }

    public static String getVMHeapInfo() {
        long maxMem = Runtime.getRuntime().maxMemory();
        long freeMem = Runtime.getRuntime().freeMemory();
        long totalMem = Runtime.getRuntime().totalMemory();
        long allocatedMem = totalMem - freeMem;
        double ratio = allocatedMem * 100L / totalMem;
        String info = String.format("VM Heap Mem : total=%s, allocated=%s (%s %%), max=%s", new Object[]{
                byteToMB(totalMem), byteToMB(allocatedMem), new DecimalFormat(
                "##.#").format(ratio), byteToMB(maxMem)});
        return info;
    }

    public static String getNativeHeapInfo() {
        long maxMem = Debug.getNativeHeapSize();
        long allocatedMem = Debug.getNativeHeapAllocatedSize();
        long freeMem = Debug.getNativeHeapFreeSize();
        double ratio = allocatedMem * 100L / maxMem;
        String info = String.format("Native Heap : max=%s, allocated=%s (%s %%), free=%s", new Object[]{
                byteToMB(maxMem), byteToMB(allocatedMem), new DecimalFormat(
                "##.#").format(ratio), byteToMB(freeMem)});
        return info;
    }

    private static String byteToMB(long byteNum) {
        byteNum /= BytesInMB;
        DecimalFormat f1 = new DecimalFormat("#,###MB");
        return f1.format(byteNum);
    }

    private static String kbToMB(long kbNum) {
        kbNum /= BytesInKB;
        DecimalFormat f1 = new DecimalFormat("#,###MB");
        return f1.format(kbNum);
    }
}
