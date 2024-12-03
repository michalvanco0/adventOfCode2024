#include <iostream>
#include <fstream>
#include <sstream>
#include <regex>
using namespace std;

int main() {
    ifstream ifs(R"(inputs\input3.txt)");
    ostringstream oss;
    oss << ifs.rdbuf();
    string input = oss.str();

    regex r(R"(mul\((\d+),(\d+)\))");
    regex rDo(R"(do\(\))");
    regex rDont(R"(don't\(\))");

    string::const_iterator pos = input.cbegin();
    int sum = 0;
    int sum2 = 0;
    bool enabled = true;

    while (pos < input.cend()) {
        auto current = pos - input.cbegin();
        smatch match, doMatch, dontMatch;
        bool hasMul = regex_search(pos, input.cend(), match, r);
        bool hasDo = regex_search(pos, input.cend(), doMatch, rDo);
        bool hasDont = regex_search(pos, input.cend(), dontMatch, rDont);
        size_t mulPos = hasMul ? current + match.position() : string::npos;
        size_t doPos = hasDo ? current + doMatch.position() : string::npos;
        size_t dontPos = hasDont ? current + dontMatch.position() : string::npos;

        if (!hasMul && !hasDo && !hasDont) break;
        size_t nextPos = string::npos;

        if (hasMul) nextPos = mulPos;
        if (hasDo && (doPos < nextPos || nextPos == string::npos)) nextPos = doPos;
        if (hasDont && (dontPos < nextPos || nextPos == string::npos)) nextPos = dontPos;
        if (nextPos == mulPos) {
            int num1 = stoi(match[1].str());
            int num2 = stoi(match[2].str());
            sum += num1 * num2;
            if (enabled) {
                sum2 += num1 * num2;
            }
            pos = match.suffix().first;
        } else if (nextPos == doPos) {
            enabled = true;
            pos = doMatch.suffix().first;
        } else if (nextPos == dontPos) {
            enabled = false;
            pos = dontMatch.suffix().first;
        } else {
            ++pos;
        }
    }

    cout << sum << endl;
    cout << sum2 << endl;
    return 0;
}
