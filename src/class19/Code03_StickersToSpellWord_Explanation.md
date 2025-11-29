# Code03_StickersToSpellWord 详细解释

## 问题描述

给定一组贴纸（stickers），每个贴纸上有若干字母，每种贴纸都有无穷张。要求用这些贴纸拼出目标字符串（target），返回最少需要多少张贴纸。如果无法拼出，返回-1。

**LeetCode链接**: https://leetcode.cn/problems/stickers-to-spell-word

## 示例

```
输入: stickers = ["with", "example", "science"], target = "thehat"
输出: 3

解释:
- 使用 "with" 贴纸: 得到 't', 'h'
- 使用 "example" 贴纸: 得到 'e', 'a' 
- 使用 "with" 贴纸: 得到 't'
- 总共需要 3 张贴纸
```

---

## 算法1: 暴力递归 (minStickers1 + process1)

### 核心思路
尝试每一张贴纸作为第一张，递归求解剩余部分。

### 代码逐行解释

```java
public static int minStickers1(String[] stickers, String target) {
    // 调用递归函数获取答案
    int ans = process1(stickers, target);
    // 如果返回 Integer.MAX_VALUE 表示无法完成，返回-1；否则返回答案
    return ans == Integer.MAX_VALUE ? -1 : ans;
}
```

**关键点**:
- 入口函数，处理返回值格式
- Integer.MAX_VALUE 作为"不可能完成"的标记

```java
// 所有贴纸stickers，每一种贴纸都有无穷张
// target
// 最少张数
public static int process1(String[] stickers, String target) {
    // Base case: 如果目标字符串为空，不需要任何贴纸
    if (target.length() == 0) {
        return 0;
    }
    // 初始化最小值为最大整数
    int min = Integer.MAX_VALUE;
    // 尝试每一张贴纸作为第一张。任何一轮可以认为以这张贴纸开始总共要多少张。
    // 这是暴力递归，所以以任何一张贴纸开始尝试，
    for (String first : stickers) {
        // 用当前贴纸减去target中能匹配的字符，得到剩余字符串
        String rest = minus(target, first);
        // 只有当rest长度变短了，说明这张贴纸有用
        if (rest.length() != target.length()) {
            // 递归求解剩余部分，更新最小值
            min = Math.min(min, process1(stickers, rest));
        }
    }
    // 如果min还是MAX_VALUE，说明无法完成，返回MAX_VALUE
    // 否则返回 min + 1（加上当前这张贴纸）
    return min + (min == Integer.MAX_VALUE ? 0 : 1);
}
```

**关键点**:
- 递归终止条件: target为空字符串
- 剪枝优化: 只考虑能减少target长度的贴纸
- 返回值处理: 如果无法完成返回MAX_VALUE，否则返回次数+1

```java
public static String minus(String s1, String s2) {
    // 将两个字符串转为字符数组
    char[] str1 = s1.toCharArray();
    char[] str2 = s2.toCharArray();
    // 用数组统计s1中每个字符的出现次数
    int[] count = new int[26];
    for (char cha : str1) {
        count[cha - 'a']++;
    }
    // 减去s2中的字符
    for (char cha : str2) {
        count[cha - 'a']--;
    }
    // 构建剩余字符串
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < 26; i++) {
        // 只保留count > 0的字符
        if (count[i] > 0) {
            for (int j = 0; j < count[i]; j++) {
                builder.append((char) (i + 'a'));
            }
        }
    }
    return builder.toString();
}
```

**关键点**:
- 使用词频统计的方式计算字符串相减
- count[i] 表示第i个字母还需要多少个
- 返回的字符串是按字母顺序排列的

### 图解示例

```
target = "thehat", stickers = ["with", "example", "science"]

                    "thehat"
                   /    |    \
              "with" "example" "science"
                /       |          \
           "ehat"     "thht"      "thehat"(无效)
          /  |  \      ...         
    "with" "example" ...
       |       |
     "ehat"   "tht"
      ...     ...
```

### 时间复杂度
O(N^M)，其中N是贴纸数量，M是递归深度（最坏情况下等于target长度）

---

## 算法2: 优化版暴力递归 (minStickers2 + process2)

### 核心优化

1. **词频表优化**: 预先将贴纸转换为词频表，避免重复转换
2. **剪枝优化**: 只选择包含target第一个字符的贴纸作为第一张

### 代码逐行解释

```java
public static int minStickers2(String[] stickers, String target) {
    int N = stickers.length;
    // 关键优化(用词频表替代贴纸数组)
    // counts[i][j] 表示第i张贴纸中字符j出现的次数
    int[][] counts = new int[N][26];
    for (int i = 0; i < N; i++) {
        char[] str = stickers[i].toCharArray();
        for (char cha : str) {
            counts[i][cha - 'a']++;
        }
    }
    // 调用优化后的递归函数
    int ans = process2(counts, target);
    return ans == Integer.MAX_VALUE ? -1 : ans;
}
```

**关键点**:
- 预处理: 将所有贴纸转换为词频表
- 避免递归中重复计算词频

```java
// stickers[i] 数组，当初i号贴纸的字符统计 int[][] stickers -> 所有的贴纸
// 每一种贴纸都有无穷张
// 返回搞定target的最少张数
// 最少张数
public static int process2(int[][] stickers, String t) {
    // Base case
    if (t.length() == 0) {
        return 0;
    }
    // target做出词频统计
    // target  aabbc  2 2 1..
    //                0 1 2..
    char[] target = t.toCharArray();
    int[] tcounts = new int[26];
    for (char cha : target) {
        tcounts[cha - 'a']++;
    }
    int N = stickers.length;
    int min = Integer.MAX_VALUE;
    for (int i = 0; i < N; i++) {
        // 尝试第一张贴纸是谁
        int[] sticker = stickers[i];
        // 最关键的优化(重要的剪枝!这一步也是贪心!)
        // 只有当前贴纸包含target的第一个字符时才考虑
        if (sticker[target[0] - 'a'] > 0) {
            StringBuilder builder = new StringBuilder();
            // 计算使用这张贴纸后的剩余字符
            for (int j = 0; j < 26; j++) {
                if (tcounts[j] > 0) {
                    // 剩余数量 = 目标需要的 - 贴纸提供的
                    int nums = tcounts[j] - sticker[j];
                    for (int k = 0; k < nums; k++) {
                        builder.append((char) (j + 'a'));
                    }
                }
            }
            String rest = builder.toString();
            // 递归求解剩余部分
            min = Math.min(min, process2(stickers, rest));
        }
    }
    return min + (min == Integer.MAX_VALUE ? 0 : 1);
}
```

**关键点**:
- **重要剪枝**: `if (sticker[target[0] - 'a'] > 0)` 
  - 为什么有效？因为target[0]这个字符总要被某张贴纸覆盖
  - 我们规定：只有包含target[0]的贴纸才能作为第一张
  - 这避免了大量重复计算（不同顺序但内容相同的方案）
- 词频相减更高效

### 图解示例

```
target = "thehat", stickers词频表 = [[w:1,i:1,t:1,h:1], [e:2,x:1,a:1,m:1,p:1,l:1], ...]

第一个字符是 't'，只考虑包含't'的贴纸

                    "thehat" (t=2,h=2,e=1,a=1)
                         |
                    选"with" (包含t)
                         |
                    "eat" (e=1,a=1,t=1)
                         |
                    选"example" (包含e)
                         |
                    "t" (t=1)
                         |
                    选"with" (包含t)
                         |
                     ""
                     
结果: 3张贴纸
```

### 为什么这个剪枝是正确的？

**证明**:
1. target[0]这个字符必须被某张贴纸覆盖
2. 假设最优解中，覆盖target[0]的贴纸是第k张
3. 我们可以把这张贴纸调整到第一张使用，结果不变
4. 因此，限制第一张贴纸必须包含target[0]不会错过最优解

---

## 算法3: 记忆化搜索 (minStickers3 + process3)

### 核心优化

在算法2的基础上，使用HashMap缓存已计算的子问题结果。

### 代码逐行解释

```java
public static int minStickers3(String[] stickers, String target) {
    int N = stickers.length;
    // 预处理词频表
    int[][] counts = new int[N][26];
    for (int i = 0; i < N; i++) {
        char[] str = stickers[i].toCharArray();
        for (char cha : str) {
            counts[i][cha - 'a']++;
        }
    }
    // 创建记忆化缓存
    HashMap<String, Integer> dp = new HashMap<>();
    // Base case: 空字符串需要0张贴纸
    dp.put("", 0);
    int ans = process3(counts, target, dp);
    return ans == Integer.MAX_VALUE ? -1 : ans;
}
```

**关键点**:
- HashMap作为缓存，key是剩余字符串，value是最少贴纸数
- 预存base case

```java
public static int process3(int[][] stickers, String t, HashMap<String, Integer> dp) {
    // 如果已经计算过，直接返回缓存结果
    if (dp.containsKey(t)) {
        return dp.get(t);
    }
    // 计算target的词频
    char[] target = t.toCharArray();
    int[] tcounts = new int[26];
    for (char cha : target) {
        tcounts[cha - 'a']++;
    }
    int N = stickers.length;
    int min = Integer.MAX_VALUE;
    // 尝试每张包含target[0]的贴纸
    for (int i = 0; i < N; i++) {
        int[] sticker = stickers[i];
        if (sticker[target[0] - 'a'] > 0) {
            StringBuilder builder = new StringBuilder();
            for (int j = 0; j < 26; j++) {
                if (tcounts[j] > 0) {
                    int nums = tcounts[j] - sticker[j];
                    for (int k = 0; k < nums; k++) {
                        builder.append((char) (j + 'a'));
                    }
                }
            }
            String rest = builder.toString();
            // 递归求解（会使用缓存）
            min = Math.min(min, process3(stickers, rest, dp));
        }
    }
    int ans = min + (min == Integer.MAX_VALUE ? 0 : 1);
    // 将结果存入缓存
    dp.put(t, ans);
    return ans;
}
```

**关键点**:
- 查缓存 → 计算 → 存缓存
- 避免重复计算相同的子问题

### 记忆化示例

```
计算过程:

第一次遇到 "eat": 计算结果 = 2, 存入dp
第二次遇到 "eat": 直接从dp返回 2

dp = {
    "": 0,
    "t": 1,
    "at": 1,
    "eat": 2,
    "thehat": 3
}
```

### 时间复杂度分析

- **算法1**: O(N^M)，指数级
- **算法2**: O(N^M)，但实际运行快得多（剪枝）
- **算法3**: O(N × 不同子问题数)，子问题数最多2^26（实际远小于此）

---

## 三种算法对比

| 算法 | 时间复杂度 | 空间复杂度 | 优化点 |
|------|-----------|-----------|--------|
| 算法1 | O(N^M) | O(M) | 无 |
| 算法2 | O(N^M) | O(M) | 词频表 + 剪枝 |
| 算法3 | O(N×S) | O(S) | 记忆化搜索 |

*N=贴纸数，M=target长度，S=不同子问题数*

---

## 完整示例演示

### 输入
```java
stickers = ["with", "example", "science"]
target = "thehat"
```

### 词频表
```
"with":    w=1, i=1, t=1, h=1
"example": e=2, x=1, a=1, m=1, p=1, l=1
"science": s=1, c=2, i=1, e=2, n=1
```

### 执行过程（算法3）

```
1. process3(stickers, "thehat", dp)
   - target[0] = 't'
   - 只有 "with" 包含 't'
   - 使用 "with": thehat - with = "eat"
   - 递归: process3(stickers, "eat", dp)

2. process3(stickers, "eat", dp)
   - target[0] = 'e'
   - "example" 和 "science" 包含 'e'
   - 尝试 "example": eat - example = "t"
   - 递归: process3(stickers, "t", dp)

3. process3(stickers, "t", dp)
   - target[0] = 't'
   - 使用 "with": t - with = ""
   - 递归: process3(stickers, "", dp)

4. process3(stickers, "", dp)
   - 已在缓存中: dp.get("") = 0
   - 返回 0

5. 回溯到步骤3: 返回 0 + 1 = 1, dp.put("t", 1)
6. 回溯到步骤2: 返回 1 + 1 = 2, dp.put("eat", 2)  
7. 回溯到步骤1: 返回 2 + 1 = 3, dp.put("thehat", 3)

最终答案: 3
```

### 使用的贴纸顺序
1. "with" (获得 t, h)
2. "example" (获得 e, a)
3. "with" (获得 t)

---

## 关键要点总结

### 1. minus函数的作用
计算 target - sticker，返回还需要的字符。

### 2. 剪枝的重要性
`if (sticker[target[0] - 'a'] > 0)` 这个条件避免了大量重复搜索。

### 3. 为什么返回MAX_VALUE
表示无法用当前贴纸完成任务，需要在上层递归中过滤掉。

### 4. 记忆化的key
使用剩余字符串作为key，因为同样的剩余字符串需要的贴纸数是固定的。

### 5. 字符串的标准化
minus函数返回的字符串是按字母顺序排列的，确保相同字符集合的字符串有相同表示，提高缓存命中率。

---

## 可能的优化方向

1. **双向BFS**: 从target和空字符串双向搜索
2. **启发式搜索**: 使用A*算法，估计剩余步数
3. **状态压缩**: 如果target字符种类少，可以用位运算优化
4. **贪心预处理**: 删除被其他贴纸完全包含的贴纸

---

## 总结

这道题是一个典型的**递归 → 剪枝 → 记忆化**的优化过程：

1. **暴力递归**: 尝试所有可能，建立递归模型
2. **剪枝优化**: 通过限制第一张贴纸减少搜索空间
3. **记忆化搜索**: 缓存子问题避免重复计算

算法3是实际应用中的最佳选择，在LeetCode上可以通过所有测试用例。

